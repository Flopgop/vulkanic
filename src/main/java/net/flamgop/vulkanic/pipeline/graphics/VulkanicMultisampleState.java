package net.flamgop.vulkanic.pipeline.graphics;

import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkPipelineMultisampleStateCreateInfo;

public record VulkanicMultisampleState(
        EnumIntBitset<VulkanicSampleCountFlag> rasterizationSamples,
        boolean sampleShadingEnable, float minSampleShading,
        int[] sampleMask, boolean alphaToCoverageEnable, boolean alphaToOneEnable
) {
    public @NotNull VkPipelineMultisampleStateCreateInfo build(MemoryStack stack) {
        return VkPipelineMultisampleStateCreateInfo.calloc(stack)
                .sType$Default()
                .rasterizationSamples(rasterizationSamples.mask())
                .sampleShadingEnable(sampleShadingEnable)
                .minSampleShading(minSampleShading)
                .pSampleMask(stack.ints(sampleMask))
                .alphaToCoverageEnable(alphaToCoverageEnable)
                .alphaToOneEnable(alphaToOneEnable);
    }
}
