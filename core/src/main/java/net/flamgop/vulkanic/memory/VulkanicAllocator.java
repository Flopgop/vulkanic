package net.flamgop.vulkanic.memory;

import net.flamgop.vulkanic.memory.image.*;
import net.flamgop.vulkanic.pipeline.graphics.VulkanicSampleCountFlag;
import net.flamgop.vulkanic.util.EnumIntBitset;
import net.flamgop.vulkanic.util.VkUtil;
import net.flamgop.vulkanic.core.VulkanicDevice;
import net.flamgop.vulkanic.core.VulkanicInstance;
import net.flamgop.vulkanic.core.VulkanicPhysicalDevice;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3i;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.vma.*;
import org.lwjgl.vulkan.*;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;

public class VulkanicAllocator implements AutoCloseable {

    private final VulkanicDevice device;
    private final long handle;

    private final boolean supportsBufferDeviceAddress;

    public VulkanicAllocator(@NotNull VulkanicInstance instance, @NotNull VulkanicPhysicalDevice physicalDevice, @NotNull VulkanicDevice device) {
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
                    .vulkanApiVersion(instance.applicationInfo().apiVersion().version())
                    .flags(Vma.VMA_ALLOCATOR_CREATE_BUFFER_DEVICE_ADDRESS_BIT);

            PointerBuffer pAllocator = stack.callocPointer(1);
            VkUtil.check(Vma.vmaCreateAllocator(createInfo, pAllocator));
            this.handle = pAllocator.get(0);
        }
    }

    public boolean supportsBufferDeviceAddress() {
        return supportsBufferDeviceAddress;
    }

    public long getBufferDeviceAddress(@NotNull VulkanicBuffer buffer) {
        if (!supportsBufferDeviceAddress) throw new UnsupportedOperationException("VulkanicAllocator#getBufferDeviceAddress requires the bufferDeviceAddress feature to be enabled!");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkBufferDeviceAddressInfo addressInfo = VkBufferDeviceAddressInfo.calloc(stack)
                    .sType$Default()
                    .buffer(buffer.handle());

            return VK12.vkGetBufferDeviceAddress(device.handle(), addressInfo);
        }
    }

    public void copyMemoryToAllocation(@NotNull ByteBuffer memory, long allocation, long offset) {
        Vma.vmaCopyMemoryToAllocation(handle, memory, allocation, offset);
    }

    public void invalidateAllocation(long allocation) {
        Vma.vmaInvalidateAllocation(handle, allocation, 0, VK10.VK_WHOLE_SIZE);
    }

    public void flushAllocation(long allocation) {
        Vma.vmaFlushAllocation(handle, allocation, 0, VK10.VK_WHOLE_SIZE);
    }

    public void freeMemory(long allocation) {
        Vma.vmaFreeMemory(this.handle, allocation);
    }

    public MappedMemory mapMemory(long allocation) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer ppData = stack.callocPointer(1);
            VkUtil.check(Vma.vmaMapMemory(this.handle, allocation, ppData));
            VmaAllocationInfo info = VmaAllocationInfo.calloc(stack);
            Vma.vmaGetAllocationInfo(this.handle, allocation, info);
            return new MappedMemory(this, allocation, ppData.get(0), info.size());
        }
    }

    public void unmapMemory(long allocation) {
        Vma.vmaUnmapMemory(this.handle, allocation);
    }

    public @NotNull VulkanicBuffer createBuffer(
            @NotNull VulkanicBufferCreateInfo bufferCreateInfo,
            @NotNull VulkanicAllocationCreateInfo allocationCreateInfo
    ) {
        if (bufferCreateInfo.usage().contains(VulkanicBufferUsageFlag.DESCRIPTOR_HEAP_EXT) && !this.device.features().supportsDescriptorHeap()) throw new UnsupportedOperationException("Cannot create a descriptor heap without the descriptor heap feature enabled.");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pBuffer = stack.callocLong(1);
            PointerBuffer pAllocation = stack.callocPointer(1);
            VmaAllocationInfo pAllocationInfo = VmaAllocationInfo.calloc(stack);

            if (bufferCreateInfo.usage().contains(VulkanicBufferUsageFlag.SHADER_DEVICE_ADDRESS)) {
                if (!this.supportsBufferDeviceAddress) throw new UnsupportedOperationException("Cannot create a buffer with usage SHADER_DEVICE_ADDRESS because this allocator does not support buffer device address.");
            } else {
                if (this.supportsBufferDeviceAddress) throw new UnsupportedOperationException("Cannot create a buffer without usage SHADER_DEVICE_ADDRESS because this allocator requires buffer device address.");
            }

            VkBufferCreateInfo pBufferCreateInfo = VkBufferCreateInfo.calloc(stack)
                    .sType$Default()
                    .size(bufferCreateInfo.size().bytes())
                    .usage(bufferCreateInfo.usage().mask())
                    .sharingMode(bufferCreateInfo.sharingMode().qualifier())
                    .queueFamilyIndexCount(bufferCreateInfo.queueFamilyIndexCount())
                    .pQueueFamilyIndices(stack.ints(bufferCreateInfo.queueFamilyIndices()));

            VmaAllocationCreateInfo pAllocationCreateInfo = VmaAllocationCreateInfo.calloc(stack)
                    .flags(allocationCreateInfo.flags().mask())
                    .usage(allocationCreateInfo.memoryUsage().qualifier())
                    .requiredFlags(allocationCreateInfo.requiredFlags().mask())
                    .preferredFlags(allocationCreateInfo.preferredFlags().mask())
                    .memoryTypeBits(allocationCreateInfo.memoryTypeBits());

            VkUtil.check(Vma.vmaCreateBuffer(this.handle, pBufferCreateInfo, pAllocationCreateInfo, pBuffer, pAllocation, pAllocationInfo));
            return new VulkanicBuffer(this, pBuffer.get(0), pAllocation.get(0), pAllocationInfo);
        }
    }

    public void destroyBuffer(@NotNull VulkanicBuffer buffer) {
        Vma.vmaDestroyBuffer(this.handle, buffer.handle(), buffer.allocation());
    }

    public @NotNull VulkanicImage createImage(
            @NotNull VulkanicImageCreateInfo imageCreateInfo,
            @NotNull VulkanicAllocationCreateInfo allocationCreateInfo) {
        if (imageCreateInfo.extent().x <= 0 || imageCreateInfo.extent().y <= 0 || imageCreateInfo.extent().z <= 0) throw new IllegalArgumentException("Cannot create an image with a 0 size!");
        try (MemoryStack stack = MemoryStack.stackPush()) {

            VmaAllocationCreateInfo pAllocationCreateInfo = VmaAllocationCreateInfo.calloc(stack)
                    .flags(allocationCreateInfo.flags().mask())
                    .usage(allocationCreateInfo.memoryUsage().qualifier())
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
            return new VulkanicImage(this, pImage.get(0), pAllocation.get(0), pAllocationInfo, imageCreateInfo);
        }
    }

    public void destroyImage(@NotNull VulkanicImage image) {
        Vma.vmaDestroyImage(this.handle, image.handle(), image.allocation());
    }

    @Override
    public void close() {
        Vma.vmaDestroyAllocator(handle);
    }
}
