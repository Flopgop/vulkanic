package net.flamgop.vulkanic.pipeline.graphics;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkPipelineDepthStencilStateCreateInfo;

public record VulkanicDepthStencilState(
        boolean depthTestEnable, boolean depthWriteEnable,
        VulkanicCompareOp depthCompareOp, boolean depthBoundsTestEnable,
        boolean stencilTestEnable, VulkanicStencilOpState front, VulkanicStencilOpState back,
        float minDepthBounds, float maxDepthBounds
) {
    public @NotNull VkPipelineDepthStencilStateCreateInfo build(MemoryStack stack) {
        return VkPipelineDepthStencilStateCreateInfo.calloc(stack)
                .sType$Default()
                .depthTestEnable(depthTestEnable)
                .depthWriteEnable(depthWriteEnable)
                .depthCompareOp(depthCompareOp.qualifier())
                .depthBoundsTestEnable(depthBoundsTestEnable)
                .stencilTestEnable(stencilTestEnable)
                .front(front::get)
                .back(back::get)
                .minDepthBounds(minDepthBounds)
                .maxDepthBounds(maxDepthBounds);
    }
}
