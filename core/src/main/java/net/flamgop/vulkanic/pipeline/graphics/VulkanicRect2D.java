package net.flamgop.vulkanic.pipeline.graphics;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.lwjgl.vulkan.VkRect2D;

public record VulkanicRect2D(Vector2ic offset, Vector2ic extent) {
    public VulkanicRect2D() {
        this(0,0,0,0);
    }

    public VulkanicRect2D(int x, int y, int w, int h) {
        this(new Vector2i(x, y), new Vector2i(w, h));
    }

    public @NotNull VkRect2D get(VkRect2D struct) {
        return struct
                .extent(e -> e.set(extent.x(), extent.y()))
                .offset(o -> o.set(offset.x(), offset.y()));
    }
}
