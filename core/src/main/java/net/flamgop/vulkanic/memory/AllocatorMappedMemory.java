package net.flamgop.vulkanic.memory;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.MemorySegment;

public final class AllocatorMappedMemory implements MappedMemory {

    private final VulkanicAllocator allocator;
    private final VulkanicAllocation allocation;
    private final MemorySegment segment;

    /// @see VulkanicBuffer#map
    /// @see net.flamgop.vulkanic.memory.image.VulkanicImage#map
    AllocatorMappedMemory(VulkanicAllocator allocator, VulkanicAllocation allocation, long address, long size) {
        this.allocator = allocator;
        this.allocation = allocation;
        this.segment = MemorySegment.ofAddress(address).reinterpret(size);
    }

    @Contract(pure = true)
    public @NotNull MemorySegment segment() {
        return segment;
    }

    @Override
    public void close() {
        allocator.unmapMemory(this.allocation);
    }
}
