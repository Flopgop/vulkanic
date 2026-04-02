package net.flamgop.vulkanic.pipeline.descriptor;

import net.flamgop.vulkanic.memory.VulkanicBuffer;
import org.jetbrains.annotations.NotNull;

public record VulkanicDescriptorBufferInfo(@NotNull VulkanicBuffer buffer, long offset, long range) {
}
