package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.math.VulkanicRect2D;
import org.jetbrains.annotations.NotNull;

public record VulkanicClearRect(
        @NotNull VulkanicRect2D rect,
        int baseArrayLayer,
        int layerCount
) {
}
