package net.flamgop.vulkanic.memory;

import net.flamgop.vulkanic.exception.VulkanException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.util.vma.VmaAllocationInfo;

public class VulkanicBuffer implements AutoCloseable {

    private final VulkanicAllocator allocator;
    private final long handle;
    private final long allocation;

    private final VulkanicBufferCreateInfo bufferCreateInfo;
    private final VulkanicAllocationCreateInfo allocationCreateInfo;

    private final int memoryType;
    private final long deviceMemory;
    private final long offset;
    private final VulkanicDeviceSize size;

    private final long deviceAddress;

    /// @see VulkanicAllocator#createBuffer
    @ApiStatus.Internal
    public VulkanicBuffer(@NotNull VulkanicAllocator allocator, long handle, long allocation, @NotNull VmaAllocationInfo allocationInfo, @NotNull VulkanicBufferCreateInfo bufferCreateInfo, @NotNull VulkanicAllocationCreateInfo allocationCreateInfo) {
        this.allocator = allocator;
        this.handle = handle;
        this.allocation = allocation;

        this.bufferCreateInfo = bufferCreateInfo;
        this.allocationCreateInfo = allocationCreateInfo;

        this.memoryType = allocationInfo.memoryType();
        this.deviceMemory = allocationInfo.deviceMemory();
        this.offset = allocationInfo.offset();
        this.size = VulkanicDeviceSize.ofBytes(allocationInfo.size());

        if (this.bufferCreateInfo.usage().contains(VulkanicBufferUsageFlag.SHADER_DEVICE_ADDRESS)) this.deviceAddress = allocator.getBufferDeviceAddress(this);
        else deviceAddress = -1;
    }

    @Contract(pure = true)
    public @NotNull VulkanicBufferCreateInfo createInfo() {
        return bufferCreateInfo;
    }

    @Contract(pure = true)
    public @NotNull VulkanicAllocationCreateInfo allocationCreateInfo() {
        return allocationCreateInfo;
    }

    public void invalidate() {
        this.allocator.invalidateAllocation(this.allocation);
    }

    public long deviceAddress() {
        if (!allocator.supportsBufferDeviceAddress()) throw new UnsupportedOperationException("VulkanicBuffer#deviceAddress requires the bufferDeviceAddress feature to be enabled!");
        return deviceAddress;
    }

    public @NotNull MappedMemory map() throws VulkanException {
        return this.allocator.mapMemory(this.allocation);
    }

    public void flush() {
        this.allocator.flushAllocation(this.allocation);
    }

    public int memoryType() {
        return this.memoryType;
    }

    public long deviceMemory() {
        return this.deviceMemory;
    }

    public long offset() {
        return this.offset;
    }

    public VulkanicDeviceSize size() {
        return this.size;
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public long allocation() {
        return allocation;
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public long handle() {
        return handle;
    }

    @Override
    public void close() {
        this.allocator.destroyBuffer(this);
    }
}
