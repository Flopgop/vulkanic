package net.flamgop.vulkanic.sync;

import net.flamgop.vulkanic.core.VulkanicDevice;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

public class VulkanicSemaphore implements AutoCloseable {
    private final VulkanicDevice device;
    private final long handle;
    private final VulkanicSemaphoreType type;

    /// @see VulkanicDevice#createSemaphore
    @ApiStatus.Internal
    public VulkanicSemaphore(VulkanicDevice device, long handle, VulkanicSemaphoreType type) {
        this.device = device;
        this.handle = handle;
        this.type = type;
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public long handle() {
        return handle;
    }

    @Override
    public void close() {
        device.destroySemaphore(this);
    }
}
