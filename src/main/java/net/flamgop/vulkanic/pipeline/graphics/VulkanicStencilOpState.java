package net.flamgop.vulkanic.pipeline.graphics;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.vulkan.VkStencilOpState;

public record VulkanicStencilOpState(
    VulkanicStencilOp failOp, VulkanicStencilOp passOp,
    VulkanicStencilOp depthFailOp, VulkanicCompareOp compareOp,
    int compareMask, int writeMask, int reference
) {
    public @NotNull VkStencilOpState get(@NotNull VkStencilOpState struct) {
        return struct
                .failOp(failOp.qualifier())
                .passOp(passOp.qualifier())
                .depthFailOp(depthFailOp.qualifier())
                .compareOp(compareOp.qualifier())
                .compareMask(compareMask)
                .writeMask(writeMask)
                .reference(reference);
    }
}
