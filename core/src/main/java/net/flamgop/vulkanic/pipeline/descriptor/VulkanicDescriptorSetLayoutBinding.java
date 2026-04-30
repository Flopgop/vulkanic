package net.flamgop.vulkanic.pipeline.descriptor;

import net.flamgop.vulkanic.memory.image.sampler.VulkanicSampler;
import net.flamgop.vulkanic.pipeline.VulkanicShaderStage;
import net.flamgop.vulkanic.util.EnumIntBitset;

import java.util.List;

public record VulkanicDescriptorSetLayoutBinding(
        int binding,
        VulkanicDescriptorType descriptorType,
        int descriptorCount,
        EnumIntBitset<VulkanicShaderStage> stageFlags,
        List<VulkanicSampler> immutableSamplers
) {
}
