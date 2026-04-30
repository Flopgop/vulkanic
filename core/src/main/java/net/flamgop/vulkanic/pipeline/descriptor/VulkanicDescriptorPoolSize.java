package net.flamgop.vulkanic.pipeline.descriptor;

import org.jetbrains.annotations.NotNull;

public record VulkanicDescriptorPoolSize(@NotNull VulkanicDescriptorType type, int descriptorCount) {

}
