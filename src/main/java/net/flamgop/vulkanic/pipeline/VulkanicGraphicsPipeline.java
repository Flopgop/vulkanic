package net.flamgop.vulkanic.pipeline;

import net.flamgop.vulkanic.core.VulkanicDevice;
import org.jetbrains.annotations.NotNull;

public final class VulkanicGraphicsPipeline implements VulkanicPipeline {

    private final VulkanicDevice device;
    private final long handle;

    public VulkanicGraphicsPipeline(@NotNull VulkanicDevice device, long handle) {
        this.device = device;
        this.handle = handle;
    }

    @Override
    public long handle() {
        return handle;
    }

    @Override
    public void close() {
        device.destroyPipeline(this);
    }
}
