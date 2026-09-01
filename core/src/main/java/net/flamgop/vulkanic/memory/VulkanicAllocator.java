package net.flamgop.vulkanic.memory;

import net.flamgop.vulkanic.exception.VulkanException;
import net.flamgop.vulkanic.memory.image.VulkanicImage;
import net.flamgop.vulkanic.memory.image.VulkanicImageCreateInfo;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

public interface VulkanicAllocator {
    boolean supportsBufferDeviceAddress();
    long getBufferDeviceAddress(@NotNull VulkanicBuffer buffer);

    VulkanicAllocation allocateMemory(@NotNull VulkanicMemoryRequirements requirements, @NotNull VulkanicAllocationCreateInfo allocationInfo);
    void freeMemory(@NotNull VulkanicAllocation allocation);

    void copyMemoryToAllocation(@NotNull ByteBuffer memory, @NotNull VulkanicAllocation allocation, long offset);
    void invalidateAllocation(@NotNull VulkanicAllocation allocation);
    void flushAllocation(@NotNull VulkanicAllocation allocation);

    @NotNull AllocatorMappedMemory mapMemory(@NotNull VulkanicAllocation allocation) throws VulkanException;
    void unmapMemory(@NotNull VulkanicAllocation allocation);

    @NotNull VulkanicBuffer createBuffer(
            @NotNull VulkanicBufferCreateInfo bufferCreateInfo,
            @NotNull VulkanicAllocationCreateInfo allocationCreateInfo
    ) throws VulkanException;
    void destroyBuffer(@NotNull VulkanicBuffer buffer);

    VulkanicImage createImage(
            @NotNull VulkanicImageCreateInfo imageCreateInfo,
            @NotNull VulkanicAllocationCreateInfo allocationCreateInfo
    ) throws VulkanException;
    void destroyImage(@NotNull VulkanicImage image);
}
