package net.flamgop.vulkanic.pipeline.graphics;

import net.flamgop.vulkanic.memory.format.VulkanicFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record VulkanicPipelineRenderingInfo(
        int viewMask,
        @NotNull List<VulkanicFormat> colorAttachmentFormats,
        @Nullable VulkanicFormat depthAttachmentFormat,
        @Nullable VulkanicFormat stencilAttachmentFormat
) {
}
