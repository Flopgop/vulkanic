package net.flamgop.vulkanic.core.descriptor;

import org.jetbrains.annotations.NotNull;

public record VulkanicDescriptorPoolSize(@NotNull VulkanicDescriptorType type, int descriptorCount) {

}
