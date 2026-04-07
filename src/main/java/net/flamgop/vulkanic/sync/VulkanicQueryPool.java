package net.flamgop.vulkanic.sync;

import net.flamgop.vulkanic.core.VulkanicDevice;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class VulkanicQueryPool implements AutoCloseable {

    private final VulkanicDevice device;
    private final long handle;

    /// @see VulkanicDevice#createQueryPool
    @ApiStatus.Internal
    public VulkanicQueryPool(@NotNull VulkanicDevice device, long handle) {
        this.device = device;
        this.handle = handle;
    }

    public void reset() {
        device.resetQueryPool(this, 0, -1);
    }

    @ApiStatus.Internal
    public long handle() {
        return handle;
    }

    @Override
    public void close() {
        this.device.destroyQueryPool(this);
    }
}
