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
        supportsBufferDeviceAddress = this.device.features().bufferDeviceAddress();
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
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pBuffer = stack.callocLong(1);
            PointerBuffer pAllocation = stack.callocPointer(1);
            VmaAllocationInfo pAllocationInfo = VmaAllocationInfo.calloc(stack);

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
            EnumIntBitset<VulkanicImageCreateFlag> flags,
            VulkanicImageType imageType,
            VulkanicFormat format,
            Vector3i extent,
            int mipLevels,
            int arrayLayers,
            VulkanicSampleCountFlag samples,
            VulkanicImageTiling tiling,
            EnumIntBitset<VulkanicImageUsageFlag> usage,
            VulkanicSharingMode sharingMode,
            VulkanicImageLayout initialLayout,
            int[] queueFamilyIndices,

            @NotNull VulkanicAllocationCreateInfo allocationCreateInfo) {
        if (extent.x <= 0 || extent.y <= 0 || extent.z <= 0) throw new IllegalArgumentException("Cannot create an image with a 0 size!");
        try (MemoryStack stack = MemoryStack.stackPush()) {

            VmaAllocationCreateInfo pAllocationCreateInfo = VmaAllocationCreateInfo.calloc(stack)
                    .flags(allocationCreateInfo.flags().mask())
                    .usage(allocationCreateInfo.memoryUsage().qualifier())
                    .requiredFlags(allocationCreateInfo.requiredFlags().mask())
                    .preferredFlags(allocationCreateInfo.preferredFlags().mask())
                    .memoryTypeBits(allocationCreateInfo.memoryTypeBits());

            VkImageCreateInfo imageCreateInfo = VkImageCreateInfo.calloc(stack)
                    .sType$Default()
                    .flags(flags.mask())
                    .imageType(imageType.qualifier())
                    .format(format.qualifier())
                    .extent(e -> e.set(extent.x(), extent.y(), extent.z()))
                    .mipLevels(mipLevels)
                    .arrayLayers(arrayLayers)
                    .samples(samples.flag())
                    .tiling(tiling.qualifier())
                    .usage(usage.mask())
                    .sharingMode(sharingMode.qualifier())
                    .initialLayout(initialLayout.qualifier())
                    .queueFamilyIndexCount(queueFamilyIndices.length)
                    .pQueueFamilyIndices(stack.ints(queueFamilyIndices));

            LongBuffer pImage = stack.callocLong(1);
            PointerBuffer pAllocation = stack.callocPointer(1);
            VmaAllocationInfo pAllocationInfo = VmaAllocationInfo.calloc(stack);

            VkUtil.check(Vma.vmaCreateImage(this.handle, imageCreateInfo, pAllocationCreateInfo, pImage, pAllocation, pAllocationInfo));
            return new VulkanicImage(this, pImage.get(0), pAllocation.get(0), pAllocationInfo, format);
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
