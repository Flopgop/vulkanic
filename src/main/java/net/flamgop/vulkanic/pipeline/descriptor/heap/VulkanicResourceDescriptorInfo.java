package net.flamgop.vulkanic.pipeline.descriptor.heap;

import net.flamgop.vulkanic.pipeline.descriptor.VulkanicDescriptorType;
import org.jetbrains.annotations.NotNull;

public record VulkanicResourceDescriptorInfo(@NotNull VulkanicDescriptorType type, @NotNull VulkanicResourceDescriptorData data) {
}
