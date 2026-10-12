package net.flamgop.vulkanic.core.descriptor.heap;

import net.flamgop.vulkanic.core.descriptor.VulkanicDescriptorType;
import org.jetbrains.annotations.NotNull;

public record VulkanicResourceDescriptorInfo(@NotNull VulkanicDescriptorType type, @NotNull VulkanicResourceDescriptorData data) {
}
