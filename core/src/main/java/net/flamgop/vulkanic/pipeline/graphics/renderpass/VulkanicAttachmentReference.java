package net.flamgop.vulkanic.pipeline.graphics.renderpass;

import net.flamgop.vulkanic.memory.image.VulkanicImageLayout;
import org.jetbrains.annotations.NotNull;

/// @param attachment is either an integer value identifying an attachment at the corresponding index in {@link VulkanicRenderPassCreateInfo#attachments()} or {@link org.lwjgl.vulkan.VK10#VK_ATTACHMENT_UNUSED} (`0xFFFFFFFF`)
/// @param layout is a {@link VulkanicImageLayout} specifying the layout the attachment uses during the subpass.
public record VulkanicAttachmentReference(
        int attachment,
        @NotNull VulkanicImageLayout layout
) {
}
