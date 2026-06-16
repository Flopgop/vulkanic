package net.flamgop.vulkanic.pipeline;

import net.flamgop.vulkanic.core.VulkanicDevice;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class VulkanicPipelineCache implements AutoCloseable {

    private final VulkanicDevice device;
    private final long handle;

    /// @see VulkanicDevice#createPipelineCache
    @ApiStatus.Internal
    public VulkanicPipelineCache(@NotNull VulkanicDevice device, long handle) {
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
        device.destroyPipelineCache(this);
    }
}
