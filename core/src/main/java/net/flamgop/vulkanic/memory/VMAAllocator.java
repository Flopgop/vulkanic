package net.flamgop.vulkanic.memory;

import net.flamgop.vulkanic.core.*;
import net.flamgop.vulkanic.exception.VulkanException;
import net.flamgop.vulkanic.memory.image.*;
import net.flamgop.vulkanic.util.VkUtil;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.vma.*;
import org.lwjgl.vulkan.*;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;

/// This is technically not eligible for implementing VulkanicObject because it's a vma object
public final class VMAAllocator implements AutoCloseable, VulkanicAllocator {

    public record Allocation(VulkanicDeviceMemory deviceMemory, VulkanicDeviceSize offset, VulkanicDeviceSize size, int memoryTypeIndex, long vmaHandle) implements VulkanicAllocation { }

    private final VulkanicDevice device;
    private final long handle;

    private final boolean supportsBufferDeviceAddress;

    public VMAAllocator(@NotNull VulkanicInstance instance, @NotNull VulkanicPhysicalDevice physicalDevice, @NotNull VulkanicDevice device) throws VulkanException {
        this.device = device;
        supportsBufferDeviceAddress = this.device.features().supportsBufferDeviceAddress();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VmaVulkanFunctions functions = VmaVulkanFunctions.calloc(stack)
                    .set(instance.handle(), device.handle());

            VmaAllocatorCreateInfo createInfo = VmaAllocatorCreateInfo.calloc(stack)
                    .instance(instance.handle())
                    .physicalDevice(physicalDevice.handle())
                    .device(device.handle())
                    .pVulkanFunctions(functions)
                    .vulkanApiVersion(instance.applicationInfo().apiVersion().version());
            if (supportsBufferDeviceAddress) createInfo.flags(Vma.VMA_ALLOCATOR_CREATE_BUFFER_DEVICE_ADDRESS_BIT);

            PointerBuffer pAllocator = stack.callocPointer(1);
            VkUtil.check(Vma.vmaCreateAllocator(createInfo, pAllocator));
            this.handle = pAllocator.get(0);
        }
    }

    @Override
    public boolean supportsBufferDeviceAddress() {
        return supportsBufferDeviceAddress;
    }

    @Override
    public long getBufferDeviceAddress(@NotNull VulkanicBuffer buffer) {
        if (!supportsBufferDeviceAddress) throw new UnsupportedOperationException("VulkanicAllocator#getBufferDeviceAddress requires the bufferDeviceAddress feature to be enabled!");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkBufferDeviceAddressInfo addressInfo = VkBufferDeviceAddressInfo.calloc(stack)
                    .sType$Default()
                    .buffer(buffer.handle());

            return VK12.vkGetBufferDeviceAddress(device.handle(), addressInfo);
        }
    }

    @Override
    public VulkanicAllocation allocateMemory(@NotNull VulkanicMemoryRequirements requirements, @NotNull VulkanicAllocationCreateInfo allocationInfo) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer pAllocation = stack.callocPointer(1);
            VmaAllocationInfo pAllocationInfo = VmaAllocationInfo.calloc(stack);

            VkMemoryRequirements pRequirements = VkMemoryRequirements.calloc(stack)
                    .size(requirements.size().bytes())
                    .alignment(requirements.alignment().bytes())
                    .memoryTypeBits(requirements.memoryTypeBits());
            VmaAllocationCreateInfo pCreateInfo = VmaAllocationCreateInfo.calloc(stack)
                    .usage(Vma.VMA_MEMORY_USAGE_AUTO)
                    .requiredFlags(allocationInfo.requiredFlags().mask())
                    .preferredFlags(allocationInfo.preferredFlags().mask())
                    .memoryTypeBits(allocationInfo.memoryTypeBits())
                    .priority(allocationInfo.priority());

            Vma.vmaAllocateMemory(handle, pRequirements, pCreateInfo, pAllocation, pAllocationInfo);

            return new Allocation(
                    new VulkanicDeviceMemory(device, pAllocationInfo.deviceMemory()),
                    VulkanicDeviceSize.ofBytes(pAllocationInfo.offset()),
                    VulkanicDeviceSize.ofBytes(pAllocationInfo.size()),
                    pAllocationInfo.memoryType(),
                    pAllocation.get(0)
            );
        }
    }

    @Override
    public void copyMemoryToAllocation(@NotNull ByteBuffer memory, @NotNull VulkanicAllocation allocation, long offset) {
        if (!(allocation instanceof Allocation alloc)) throw new UnsupportedOperationException("VMA allocator can only copy to allocations made by VMA");
        Vma.vmaCopyMemoryToAllocation(handle, memory, alloc.vmaHandle(), offset);
    }

    @Override
    public void invalidateAllocation(@NonNull VulkanicAllocation allocation) {
        if (!(allocation instanceof Allocation alloc)) throw new UnsupportedOperationException("VMA allocator can only invalidate allocations made by VMA");
        Vma.vmaInvalidateAllocation(handle, alloc.vmaHandle(), 0, VK10.VK_WHOLE_SIZE);
    }

    @Override
    public void flushAllocation(@NonNull VulkanicAllocation allocation) {
        if (!(allocation instanceof Allocation alloc)) throw new UnsupportedOperationException("VMA allocator can only flush allocations made by VMA");
        Vma.vmaFlushAllocation(handle, alloc.vmaHandle(), 0, VK10.VK_WHOLE_SIZE);
    }

    @Override
    public void freeMemory(@NonNull VulkanicAllocation allocation) {
        if (!(allocation instanceof Allocation alloc)) throw new UnsupportedOperationException("VMA allocator can only free allocations made by VMA");
        Vma.vmaFreeMemory(this.handle, alloc.vmaHandle());
    }

    @Override
    public @NonNull AllocatorMappedMemory mapMemory(@NonNull VulkanicAllocation allocation) throws VulkanException {
        if (!(allocation instanceof Allocation alloc)) throw new UnsupportedOperationException("VMA allocator can only map allocations made by VMA");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer ppData = stack.callocPointer(1);
            VkUtil.check(Vma.vmaMapMemory(this.handle, alloc.vmaHandle(), ppData));
            VmaAllocationInfo info = VmaAllocationInfo.calloc(stack);
            Vma.vmaGetAllocationInfo(this.handle, alloc.vmaHandle(), info);
            return new AllocatorMappedMemory(this, allocation, ppData.get(0), info.size());
        }
    }

    @Override
    public void unmapMemory(@NonNull VulkanicAllocation allocation) {
        if (!(allocation instanceof Allocation alloc)) throw new UnsupportedOperationException("VMA allocator can only unmap allocations made by VMA");
        Vma.vmaUnmapMemory(this.handle, alloc.vmaHandle());
    }

    public @NotNull VulkanicBuffer createBuffer(
            @NotNull VulkanicBufferCreateInfo bufferCreateInfo,
            @NotNull VulkanicAllocationCreateInfo allocationCreateInfo
    ) throws VulkanException {
        if (bufferCreateInfo.usage().contains(VulkanicBufferUsageFlag.DESCRIPTOR_HEAP_EXT) && !this.device.features().supportsDescriptorHeap()) throw new UnsupportedOperationException("Cannot create a descriptor heap without the descriptor heap feature enabled.");
        if (bufferCreateInfo.usage().contains(VulkanicBufferUsageFlag.SHADER_DEVICE_ADDRESS) && !this.supportsBufferDeviceAddress) throw new UnsupportedOperationException("Cannot create a buffer with usage SHADER_DEVICE_ADDRESS because this allocator does not support buffer device address.");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pBuffer = stack.callocLong(1);
            PointerBuffer pAllocation = stack.callocPointer(1);
            VmaAllocationInfo pAllocationInfo = VmaAllocationInfo.calloc(stack);

            VkBufferCreateInfo pBufferCreateInfo = VkBufferCreateInfo.calloc(stack)
                    .sType$Default()
                    .size(bufferCreateInfo.size().bytes())
                    .usage(bufferCreateInfo.usage().mask())
                    .sharingMode(bufferCreateInfo.sharingMode().qualifier())
                    .queueFamilyIndexCount(bufferCreateInfo.queueFamilyIndices().length)
                    .pQueueFamilyIndices(stack.ints(bufferCreateInfo.queueFamilyIndices()));

            VmaAllocationCreateInfo pAllocationCreateInfo = VmaAllocationCreateInfo.calloc(stack)
                    .usage(Vma.VMA_MEMORY_USAGE_AUTO)
                    .requiredFlags(allocationCreateInfo.requiredFlags().mask())
                    .preferredFlags(allocationCreateInfo.preferredFlags().mask())
                    .memoryTypeBits(allocationCreateInfo.memoryTypeBits())
                    .priority(allocationCreateInfo.priority());

            VkUtil.check(Vma.vmaCreateBuffer(this.handle, pBufferCreateInfo, pAllocationCreateInfo, pBuffer, pAllocation, pAllocationInfo));
            VulkanicAllocation allocation = new Allocation(
                    new VulkanicDeviceMemory(device, pAllocationInfo.deviceMemory()),
                    VulkanicDeviceSize.ofBytes(pAllocationInfo.offset()),
                    VulkanicDeviceSize.ofBytes(pAllocationInfo.size()),
                    pAllocationInfo.memoryType(),
                    pAllocation.get(0)
            );

            return new VulkanicBuffer(this, pBuffer.get(0), allocation, bufferCreateInfo, allocationCreateInfo);
        }
    }

    @Override
    public void destroyBuffer(@NotNull VulkanicBuffer buffer) {
        if (!(buffer.allocation() instanceof Allocation alloc)) throw new UnsupportedOperationException("VMA allocator can only destroy buffers made by VMA");
        Vma.vmaDestroyBuffer(this.handle, buffer.handle(), alloc.vmaHandle());
    }

    @Override
    public @NotNull VulkanicImage createImage(
            @NotNull VulkanicImageCreateInfo imageCreateInfo,
            @NotNull VulkanicAllocationCreateInfo allocationCreateInfo) throws VulkanException {
        if (imageCreateInfo.extent().x <= 0 || imageCreateInfo.extent().y <= 0 || imageCreateInfo.extent().z <= 0) throw new IllegalArgumentException("Cannot create an image with a 0 size!");
        try (MemoryStack stack = MemoryStack.stackPush()) {

            VmaAllocationCreateInfo pAllocationCreateInfo = VmaAllocationCreateInfo.calloc(stack)
                    .usage(Vma.VMA_MEMORY_USAGE_AUTO)
                    .requiredFlags(allocationCreateInfo.requiredFlags().mask())
                    .preferredFlags(allocationCreateInfo.preferredFlags().mask())
                    .memoryTypeBits(allocationCreateInfo.memoryTypeBits());

            VkImageCreateInfo pImageCreateInfo = VkImageCreateInfo.calloc(stack)
                    .sType$Default()
                    .flags(imageCreateInfo.flags().mask())
                    .imageType(imageCreateInfo.imageType().qualifier())
                    .format(imageCreateInfo.format().qualifier())
                    .extent(e -> e.set(imageCreateInfo.extent().x(), imageCreateInfo.extent().y(), imageCreateInfo.extent().z()))
                    .mipLevels(imageCreateInfo.mipLevels())
                    .arrayLayers(imageCreateInfo.arrayLayers())
                    .samples(imageCreateInfo.samples().flag())
                    .tiling(imageCreateInfo.tiling().qualifier())
                    .usage(imageCreateInfo.usage().mask())
                    .sharingMode(imageCreateInfo.sharingMode().qualifier())
                    .initialLayout(imageCreateInfo.initialLayout().qualifier())
                    .queueFamilyIndexCount(imageCreateInfo.queueFamilyIndices().length)
                    .pQueueFamilyIndices(stack.ints(imageCreateInfo.queueFamilyIndices()));

            LongBuffer pImage = stack.callocLong(1);
            PointerBuffer pAllocation = stack.callocPointer(1);
            VmaAllocationInfo pAllocationInfo = VmaAllocationInfo.calloc(stack);

            VkUtil.check(Vma.vmaCreateImage(this.handle, pImageCreateInfo, pAllocationCreateInfo, pImage, pAllocation, pAllocationInfo));
            VulkanicAllocation allocation = new Allocation(
                    new VulkanicDeviceMemory(device, pAllocationInfo.deviceMemory()),
                    VulkanicDeviceSize.ofBytes(pAllocationInfo.offset()),
                    VulkanicDeviceSize.ofBytes(pAllocationInfo.size()),
                    pAllocationInfo.memoryType(),
                    pAllocation.get(0)
            );

            return new VulkanicImage(this, pImage.get(0), allocation, pAllocationInfo, imageCreateInfo);
        }
    }

    @Override
    public void destroyImage(@NotNull VulkanicImage image) {
        if (!(image.allocation() instanceof Allocation alloc)) throw new UnsupportedOperationException("VMA allocator can only destroy buffers made by VMA");
        Vma.vmaDestroyImage(this.handle, image.handle(), alloc.vmaHandle());
    }

    @Override
    public void close() {
        Vma.vmaDestroyAllocator(handle);
    }

    public long handle() {
        return handle;
    }
}
