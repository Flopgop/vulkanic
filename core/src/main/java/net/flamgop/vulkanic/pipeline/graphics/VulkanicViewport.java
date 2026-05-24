package net.flamgop.vulkanic.pipeline.graphics;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.vulkan.VkViewport;

public record VulkanicViewport(float x, float y, float width, float height, float minDepth, float maxDepth) {
    public VulkanicViewport() {
        this(0,0,0,0,0,0);
    }

    public @NotNull VkViewport get(VkViewport struct) {
        return struct
                .x(x)
                .y(y)
                .width(width)
                .height(height)
                .minDepth(minDepth)
                .maxDepth(maxDepth);
    }
}
