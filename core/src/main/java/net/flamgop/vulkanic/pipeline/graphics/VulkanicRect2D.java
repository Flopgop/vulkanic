package net.flamgop.vulkanic.pipeline.graphics;

import net.flamgop.vulkanic.math.Int2;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.vulkan.VkRect2D;

public record VulkanicRect2D(Int2 offset, Int2 extent) {
    public VulkanicRect2D() {
        this(0,0,0,0);
    }

    public VulkanicRect2D(int x, int y, int w, int h) {
        this(new Int2.Impl(x, y), new Int2.Impl(w, h));
    }

    @SuppressWarnings("UnusedReturnValue")
    public @NotNull VkRect2D get(VkRect2D struct) {
        return struct
                .extent(e -> e.set(extent.x(), extent.y()))
                .offset(o -> o.set(offset.x(), offset.y()));
    }
}
