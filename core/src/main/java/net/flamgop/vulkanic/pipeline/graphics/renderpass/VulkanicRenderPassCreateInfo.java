package net.flamgop.vulkanic.pipeline.graphics.renderpass;

import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record VulkanicRenderPassCreateInfo(
        @NotNull EnumIntBitset<VulkanicRenderPassCreateFlag> flags,
        @NotNull List<VulkanicAttachmentDescription> attachments,
        @NotNull List<VulkanicSubpassDescription> subpasses,
        @NotNull List<VulkanicSubpassDependency> dependencies
        ) {
}
