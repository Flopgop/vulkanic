package net.flamgop.vulkanic.memory.copy;

import net.flamgop.vulkanic.math.Int3;
import net.flamgop.vulkanic.memory.image.VulkanicImageSubresourceLayers;
import org.jetbrains.annotations.NotNull;

public record VulkanicImageCopy(
        @NotNull VulkanicImageSubresourceLayers srcSubresource,
        @NotNull Int3 srcOffset,
        @NotNull VulkanicImageSubresourceLayers dstSubresource,
        @NotNull Int3 dstOffset,
        @NotNull Int3 extent
) {
}
