package net.flamgop.vulkanic.pipeline.graphics;

import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkPipelineRasterizationStateCreateInfo;

public record VulkanicRasterizationState(
        boolean depthClampEnable,
        boolean rasterizerDiscardEnable,
        VulkanicPolygonMode polygonMode,
        EnumIntBitset<VulkanicCullModeFlag> cullMode,
        VulkanicFrontFace frontFace,
        boolean depthBiasEnable,
        float depthBiasConstantFactor,
        float depthBiasClamp,
        float depthBiasSlopeFactor,
        float lineWidth
) {
    public @NotNull VkPipelineRasterizationStateCreateInfo build(MemoryStack stack) {
        return VkPipelineRasterizationStateCreateInfo.calloc(stack)
                .sType$Default()
                .depthClampEnable(depthClampEnable)
                .rasterizerDiscardEnable(rasterizerDiscardEnable)
                .polygonMode(polygonMode.qualifier())
                .cullMode(cullMode.mask())
                .frontFace(frontFace.qualifier())
                .depthBiasEnable(depthBiasEnable)
                .depthBiasConstantFactor(depthBiasConstantFactor)
                .depthBiasClamp(depthBiasClamp)
                .depthBiasSlopeFactor(depthBiasSlopeFactor)
                .lineWidth(lineWidth);
    }
}
