package net.flamgop.vulkanic.pipeline.graphics.renderpass;

import net.flamgop.vulkanic.core.VulkanicDevice;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

public final class VulkanicFramebuffer implements AutoCloseable {
    private final VulkanicDevice device;
    private final long handle;

    /// @see VulkanicDevice#createFramebuffer
    @ApiStatus.Internal
    public VulkanicFramebuffer(VulkanicDevice device, long handle) {
        this.device = device;
        this.handle = handle;
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public long handle() {
        return handle;
    }

    @Override
    public void close() {
        device.destroyFramebuffer(this);
    }
}
