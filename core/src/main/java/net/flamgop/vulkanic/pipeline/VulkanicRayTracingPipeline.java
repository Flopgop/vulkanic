package net.flamgop.vulkanic.pipeline;

import net.flamgop.vulkanic.core.VulkanicDevice;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class VulkanicRayTracingPipeline implements VulkanicPipeline {
    private final VulkanicDevice device;
    private final long handle;

    private final VulkanicRayTracingPipelineCreateInfo createInfo;

    /// @see VulkanicDevice#createRayTracingPipeline
    @ApiStatus.Internal
    public VulkanicRayTracingPipeline(@NotNull VulkanicDevice device, long handle, @NotNull VulkanicRayTracingPipelineCreateInfo createInfo) {
        this.device = device;
        this.handle = handle;
        this.createInfo = createInfo;
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public long handle() {
        return handle;
    }

    public @NotNull VulkanicRayTracingPipelineCreateInfo createInfo() {
        return createInfo;
    }

    @Override
    public void close() {
        device.destroyPipeline(this);
    }
}
