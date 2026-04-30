package net.flamgop.vulkanic.pipeline.descriptor;

import net.flamgop.vulkanic.memory.image.VulkanicImageLayout;
import net.flamgop.vulkanic.memory.image.VulkanicImageView;
import net.flamgop.vulkanic.memory.image.sampler.VulkanicSampler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record VulkanicDescriptorImageInfo(@Nullable VulkanicSampler sampler, @Nullable VulkanicImageView imageView, @NotNull VulkanicImageLayout imageLayout) {
}
