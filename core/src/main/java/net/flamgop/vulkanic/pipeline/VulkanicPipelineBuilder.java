package net.flamgop.vulkanic.pipeline;

import net.flamgop.vulkanic.exception.VulkanException;
import org.jetbrains.annotations.NotNull;

public sealed interface VulkanicPipelineBuilder permits VulkanicGraphicsPipelineBuilder {
    @NotNull VulkanicPipeline build(@NotNull VulkanicPipelineCache pipelineCache) throws VulkanException;
}
