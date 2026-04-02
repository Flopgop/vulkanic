package net.flamgop.vulkanic.pipeline.descriptor;

import org.jetbrains.annotations.NotNull;

public record VulkanicCopyDescriptorSet(
        @NotNull VulkanicDescriptorSet srcSet,
        int srcBinding,
        int srcArrayElement,
        @NotNull VulkanicDescriptorSet dstSet,
        int dstBinding,
        int dstArrayElement,
        int descriptorCount
        ) implements VulkanicUpdateDescriptorSet {
}
