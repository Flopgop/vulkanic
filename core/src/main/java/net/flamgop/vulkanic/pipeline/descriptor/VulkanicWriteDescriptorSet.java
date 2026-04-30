package net.flamgop.vulkanic.pipeline.descriptor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record VulkanicWriteDescriptorSet(
        @NotNull VulkanicDescriptorSet dstSet,
        int dstBinding,
        int dstArrayElement,
        int descriptorCount,
        @NotNull VulkanicDescriptorType descriptorType,
        @Nullable List<@NotNull VulkanicDescriptorImageInfo> imageInfos,
        @Nullable List<@NotNull VulkanicDescriptorBufferInfo> bufferInfos,
        long @Nullable [] texelBufferView
) implements VulkanicUpdateDescriptorSet {
}
