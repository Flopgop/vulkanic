package net.flamgop.vulkanic.pipeline.graphics;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.lwjgl.vulkan.VkRect2D;

public record VulkanicRect2D(Vector2ic offset, Vector2ic extent) {
    public @NotNull VkRect2D get(VkRect2D struct) {
        return struct
                .extent(e -> e.set(extent.x(), extent.y()))
                .offset(o -> o.set(offset.x(), offset.y()));
    }
}
