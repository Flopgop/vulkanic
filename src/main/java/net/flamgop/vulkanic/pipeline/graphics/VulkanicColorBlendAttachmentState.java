package net.flamgop.vulkanic.pipeline.graphics;

import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.vulkan.VkPipelineColorBlendAttachmentState;

public record VulkanicColorBlendAttachmentState(
        boolean blendEnable,
        VulkanicBlendFactor srcColorBlendFactor, VulkanicBlendFactor dstColorBlendFactor, VulkanicBlendOp colorBlendOp,
        VulkanicBlendFactor srcAlphaBlendFactor, VulkanicBlendFactor dstAlphaBlendFactor, VulkanicBlendOp alphaBlendOp,
        EnumIntBitset<VulkanicColorComponentFlag> colorWriteMask
) {
    public VulkanicColorBlendAttachmentState() {
        this(false,
                VulkanicBlendFactor.ONE, VulkanicBlendFactor.ZERO, VulkanicBlendOp.ADD,
                VulkanicBlendFactor.ONE, VulkanicBlendFactor.ZERO, VulkanicBlendOp.ADD,
                EnumIntBitset.of(VulkanicColorComponentFlag.R, VulkanicColorComponentFlag.G, VulkanicColorComponentFlag.B, VulkanicColorComponentFlag.A)
        );
    }

    public @NotNull VkPipelineColorBlendAttachmentState get(@NotNull VkPipelineColorBlendAttachmentState state) {
        return state
                .blendEnable(blendEnable)
                .srcColorBlendFactor(srcColorBlendFactor.qualifier())
                .dstColorBlendFactor(dstColorBlendFactor.qualifier())
                .colorBlendOp(colorBlendOp.qualifier())
                .srcAlphaBlendFactor(srcAlphaBlendFactor.qualifier())
                .dstAlphaBlendFactor(dstAlphaBlendFactor.qualifier())
                .alphaBlendOp(alphaBlendOp.qualifier())
                .colorWriteMask(colorWriteMask.mask());

    }
}
