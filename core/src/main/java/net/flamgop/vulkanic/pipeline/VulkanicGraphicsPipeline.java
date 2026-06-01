package net.flamgop.vulkanic.pipeline;

import net.flamgop.vulkanic.core.VulkanicDevice;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class VulkanicGraphicsPipeline implements VulkanicPipeline {

    private final VulkanicDevice device;
    private final long handle;

    private final VulkanicGraphicsPipelineCreateInfo createInfo;

    /// @see VulkanicDevice#createGraphicsPipeline
    /// @see VulkanicDevice#createGraphicsPipelineBuilder
    @ApiStatus.Internal
    public VulkanicGraphicsPipeline(@NotNull VulkanicDevice device, long handle, @NotNull VulkanicGraphicsPipelineCreateInfo createInfo) {
        this.device = device;
        this.handle = handle;
        this.createInfo = createInfo;
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public long handle() {
        return handle;
    }

    public @NotNull VulkanicGraphicsPipelineCreateInfo createInfo() {
        return createInfo;
    }

    @Override
    public void close() {
        device.destroyPipeline(this);
    }
}
