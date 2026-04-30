package net.flamgop.vulkanic.pipeline;

import org.jetbrains.annotations.NotNull;

public sealed interface VulkanicPipelineBuilder permits VulkanicGraphicsPipelineBuilder {
    @NotNull VulkanicPipeline build();
}
