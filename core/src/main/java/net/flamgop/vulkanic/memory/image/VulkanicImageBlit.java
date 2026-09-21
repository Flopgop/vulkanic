package net.flamgop.vulkanic.memory.image;

import org.jetbrains.annotations.NotNull;

public record VulkanicImageBlit(
        @NotNull VulkanicImageSubresourceLayers srcSubresource,
        @NotNull VulkanicBounds srcBounds,
        @NotNull VulkanicImageSubresourceLayers dstSubresource,
        @NotNull VulkanicBounds dstBounds
) {
}
