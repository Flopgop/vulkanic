package net.flamgop.vulkanic.memory;

import net.flamgop.vulkanic.core.VulkanicDevice;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.MemorySegment;

public final class DeviceMappedMemory implements MappedMemory {
    
    private final VulkanicDevice device;
    private final VulkanicDeviceMemory memory;
    private final MemorySegment segment;
    
    /// @see VulkanicDevice#mapMemory
    @ApiStatus.Internal
    public DeviceMappedMemory(VulkanicDevice device, VulkanicDeviceMemory memory, long address, long size) {
        this.device = device;
        this.memory = memory;
        this.segment = MemorySegment.ofAddress(address).reinterpret(size);
    }
    
    @Override
    public @NotNull MemorySegment segment() {
        return segment;
    }

    @Override
    public void close() {
        device.unmapMemory(memory);
    }
}
