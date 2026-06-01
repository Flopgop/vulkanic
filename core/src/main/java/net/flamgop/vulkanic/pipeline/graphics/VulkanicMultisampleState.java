package net.flamgop.vulkanic.pipeline.graphics;

import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkPipelineMultisampleStateCreateInfo;

public record VulkanicMultisampleState(
        @NotNull EnumIntBitset<VulkanicSampleCountFlag> rasterizationSamples,
        boolean sampleShadingEnable, float minSampleShading,
        int @Nullable [] sampleMask, boolean alphaToCoverageEnable, boolean alphaToOneEnable
) {
    public @NotNull VkPipelineMultisampleStateCreateInfo build(MemoryStack stack) {
        return VkPipelineMultisampleStateCreateInfo.calloc(stack)
                .sType$Default()
                .rasterizationSamples(rasterizationSamples.mask())
                .sampleShadingEnable(sampleShadingEnable)
                .minSampleShading(minSampleShading)
                .pSampleMask(sampleMask != null ? stack.ints(sampleMask) : stack.ints(-1))
                .alphaToCoverageEnable(alphaToCoverageEnable)
                .alphaToOneEnable(alphaToOneEnable);
    }
}
