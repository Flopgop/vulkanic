package net.flamgop.vulkanic.pipeline.graphics.renderpass;

import net.flamgop.vulkanic.pipeline.VulkanicPipelineBindPoint;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/// @param flags is a bitset of {@link VulkanicSubpassDescriptionFlag} specifying the usage of the subpass
/// @param pipelineBindPoint is a {@link VulkanicPipelineBindPoint} specifying the pipeline type supported for this subpass.
/// @param inputAttachments is a list of {@link VulkanicAttachmentReference} objects defining the input attachemtns for this subpass and their layouts
/// @param colorAttachments is a list of {@link VulkanicAttachmentReference} objects defining the color attachments for this subpass and their layouts
/// @param resolveAttachments is `null` or a list of {@link VulkanicAttachmentReference} objects defining the resolve attachments for this subpass and their layouts
/// @param depthStencilAttachment is a {@link VulkanicAttachmentReference} object specifying the depth/stencil attachment for this subpass and its layout
/// @param preserveAttachments is a list of render pass attachment indices identifying attachments that are not used by this subpass, but whose contents **must** be preserved throughout the subpass.
public record VulkanicSubpassDescription(
        @NotNull EnumIntBitset<VulkanicSubpassDescriptionFlag> flags,
        @NotNull VulkanicPipelineBindPoint pipelineBindPoint,
        @NotNull List<VulkanicAttachmentReference> inputAttachments,
        @NotNull List<VulkanicAttachmentReference> colorAttachments,
        @Nullable List<VulkanicAttachmentReference> resolveAttachments,
        @Nullable VulkanicAttachmentReference depthStencilAttachment,
        @NotNull List<Integer> preserveAttachments
) {
}
