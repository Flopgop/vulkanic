package net.flamgop.vulkanic.memory.image;

import net.flamgop.vulkanic.math.Int3;
import org.jetbrains.annotations.NotNull;

public record VulkanicImageResolve(
        @NotNull VulkanicImageSubresourceLayers srcSubresource,
        @NotNull Int3 srcOffset,
        @NotNull VulkanicImageSubresourceLayers dstSubresource,
        @NotNull Int3 dstOffset,
        @NotNull Int3 extent
) {
}
