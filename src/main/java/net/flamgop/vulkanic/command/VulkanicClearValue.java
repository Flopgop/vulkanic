package net.flamgop.vulkanic.command;

import org.lwjgl.vulkan.VkClearValue;

public sealed interface VulkanicClearValue {
    record Color(float r, float g, float b, float a) implements VulkanicClearValue {
        public static Color black() { return new Color(0f, 0f, 0f, 1f); }
    }

    record DepthStencil(float depth, int stencil) implements VulkanicClearValue { }

    default void copyTo(VkClearValue target) {
        if (this instanceof Color(float r, float g, float b, float a)) {
            target.color().float32(0, r).float32(1, g).float32(2, b).float32(3, a);
        } else if (this instanceof DepthStencil(float depth, int stencil)) {
            target.depthStencil().set(depth, stencil);
        }
    }
}
