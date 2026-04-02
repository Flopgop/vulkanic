package net.flamgop.vulkanic.pipeline.graphics;

import net.flamgop.vulkanic.core.VulkanicDevice;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

public class VulkanicRenderPass implements AutoCloseable {

    private final VulkanicDevice device;
    private final long handle;

    @ApiStatus.Internal
    public VulkanicRenderPass(VulkanicDevice device, long handle) {
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
        device.destroyRenderPass(this);
    }
}
