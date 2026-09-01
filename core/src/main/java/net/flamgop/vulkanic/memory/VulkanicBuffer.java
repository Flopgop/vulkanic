package net.flamgop.vulkanic.memory;

import net.flamgop.vulkanic.core.VulkanicObject;
import net.flamgop.vulkanic.core.VulkanicObjectType;
import net.flamgop.vulkanic.exception.VulkanException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class VulkanicBuffer implements AutoCloseable, VulkanicObject.Opaque {

    private final VulkanicAllocator allocator;
    private final long handle;
    private final VulkanicAllocation allocation;

    private final VulkanicBufferCreateInfo bufferCreateInfo;
    private final VulkanicAllocationCreateInfo allocationCreateInfo;

    private final long deviceAddress;

    /// @see VMAAllocator#createBuffer
    @ApiStatus.Internal
    public VulkanicBuffer(@NotNull VulkanicAllocator allocator, long handle, @NotNull VulkanicAllocation allocation, @NotNull VulkanicBufferCreateInfo bufferCreateInfo, @NotNull VulkanicAllocationCreateInfo allocationCreateInfo) {
        this.allocator = allocator;
        this.handle = handle;
        this.allocation = allocation;

        this.bufferCreateInfo = bufferCreateInfo;
        this.allocationCreateInfo = allocationCreateInfo;

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

    public @NotNull AllocatorMappedMemory map() throws VulkanException {
        return this.allocator.mapMemory(this.allocation);
    }

    public void flush() {
        this.allocator.flushAllocation(this.allocation);
    }

    public int memoryType() {
        return this.allocation.memoryTypeIndex();
    }

    public VulkanicDeviceMemory deviceMemory() {
        return this.allocation.deviceMemory();
    }

    public VulkanicDeviceSize offset() {
        return this.allocation.offset();
    }

    public VulkanicDeviceSize size() {
        return this.allocation.size();
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public VulkanicAllocation allocation() {
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

    @Override
    public @NotNull VulkanicObjectType objectType() {
        return VulkanicObjectType.BUFFER;
    }
}
