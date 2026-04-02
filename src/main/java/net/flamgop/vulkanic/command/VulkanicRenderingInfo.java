package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.pipeline.graphics.VulkanicRect2D;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record VulkanicRenderingInfo(
        @NotNull EnumIntBitset<VulkanicRenderingFlag> flags,
        @NotNull VulkanicRect2D renderArea,
        int layerCount, int viewMask,
        @NotNull List<VulkanicRenderingAttachmentInfo> colorAttachments,
        @Nullable VulkanicRenderingAttachmentInfo depthAttachment,
        @Nullable VulkanicRenderingAttachmentInfo stencilAttachment
) {
}
