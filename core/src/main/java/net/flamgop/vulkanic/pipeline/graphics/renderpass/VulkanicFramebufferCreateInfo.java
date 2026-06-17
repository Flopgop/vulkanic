package net.flamgop.vulkanic.pipeline.graphics.renderpass;

import net.flamgop.vulkanic.memory.image.VulkanicImageView;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/// @param flags is a bitset of {@link VulkanicFramebufferCreateFlag}
/// @param renderPass is a render pass defining what render passes the framebuffer will be compatible with. See [Render Pass Compatibility](https://docs.vulkan.org/spec/latest/chapters/renderpass.html#renderpass-compatibility) on the Vulkan documentation for details. The implementation **must** not access this object outside the duration of the command this structure is passed to.
/// @param attachments is a list of {@link VulkanicImageView} objects, each of which will be used as the corresponding attachment in a render pass instance. If `flags` includes {@link VulkanicFramebufferCreateFlag#IMAGELESS}, this parameter is ignored.
/// @param width defines the width of the framebuffer.
/// @param height defines the height of the framebuffer.
/// @param layers define the number of layers in the framebuffer. If the render pass uses multiview, then `layers` **must** be one and each attachment requires a number of layers that is greater than the maximum bit index set in the view mask in the subpasses in which it is used.
public record VulkanicFramebufferCreateInfo(
        @NotNull EnumIntBitset<VulkanicFramebufferCreateFlag> flags,
        @NotNull VulkanicRenderPass renderPass,
        @Nullable List<VulkanicImageView> attachments,
        int width, int height, int layers
        ) {
}
