package net.flamgop.vulkanic.pipeline;

import net.flamgop.vulkanic.core.VulkanicDevice;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class VulkanicComputePipeline implements VulkanicPipeline {

    private final VulkanicDevice device;
    private final long handle;

    private final VulkanicComputePipelineCreateInfo createInfo;

    /// @see VulkanicDevice#createComputePipeline
    @ApiStatus.Internal
    public VulkanicComputePipeline(@NotNull VulkanicDevice device, long handle, @NotNull VulkanicComputePipelineCreateInfo createInfo) {
        this.device = device;
        this.handle = handle;
        this.createInfo = createInfo;
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public long handle() {
        return handle;
    }

    public @NotNull VulkanicComputePipelineCreateInfo createInfo() {
        return createInfo;
    }

    @Override
    public void close() {
        device.destroyPipeline(this);
    }
}
