package net.flamgop.vulkanic.pipeline.graphics.renderpass;

import net.flamgop.vulkanic.command.VulkanicAttachmentLoadOp;
import net.flamgop.vulkanic.command.VulkanicAttachmentStoreOp;
import net.flamgop.vulkanic.memory.format.VulkanicFormat;
import net.flamgop.vulkanic.memory.image.VulkanicImageLayout;
import net.flamgop.vulkanic.pipeline.graphics.VulkanicSampleCountFlag;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;

public record VulkanicAttachmentDescription(
        @NotNull EnumIntBitset<VulkanicAttachmentDescriptionFlag> flags,
        @NotNull VulkanicFormat format,
        @NotNull VulkanicSampleCountFlag samples,
        @NotNull VulkanicAttachmentLoadOp loadOp,
        @NotNull VulkanicAttachmentStoreOp storeOp,
        @NotNull VulkanicAttachmentLoadOp stencilLoadOp,
        @NotNull VulkanicAttachmentStoreOp stencilStoreOp,
        @NotNull VulkanicImageLayout initialLayout,
        @NotNull VulkanicImageLayout finalLayout
) {
}
