package net.flamgop.vulkanic.memory.image;

import net.flamgop.vulkanic.memory.format.VulkanicFormat;
import org.jetbrains.annotations.NotNull;

public record VulkanicImageViewCreateInfo(
        @NotNull VulkanicImageViewType viewType,
        @NotNull VulkanicFormat format,
        @NotNull VulkanicComponentMapping componentMapping,
        @NotNull VulkanicImageSubresourceRange subresourceRange
) {
}
