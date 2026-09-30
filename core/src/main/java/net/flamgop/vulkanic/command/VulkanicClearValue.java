package net.flamgop.vulkanic.command;

import org.jetbrains.annotations.Contract;
import org.lwjgl.vulkan.VkClearValue;

public sealed interface VulkanicClearValue {
    record ColorF32(float r, float g, float b, float a) implements VulkanicClearValue {
        @Contract(pure = true, value = "-> new")
        public static ColorF32 black() { return new ColorF32(0f, 0f, 0f, 1f); }
        @Contract(pure = true, value = "-> new")
        public static ColorF32 zero() { return new ColorF32(0f, 0f, 0f, 0f); }
        @Contract(pure = true, value = "-> new")
        public static ColorF32 one() { return new ColorF32(1f, 1f, 1f, 1f); }
    }

    record ColorI32(int r, int g, int b, int a) implements VulkanicClearValue {
        @Contract(pure = true, value = "-> new")
        public static ColorI32 black() { return new ColorI32(0, 0, 0, Integer.MAX_VALUE); }
        @Contract(pure = true, value = "-> new")
        public static ColorI32 zero() { return new ColorI32(0, 0, 0, 0); }
        @Contract(pure = true, value = "-> new")
        public static ColorI32 one() { return new ColorI32(1, 1, 1, 1); }
    }

    record ColorU32(int r, int g, int b, int a) implements VulkanicClearValue {
        @Contract(pure = true, value = "-> new")
        public static ColorU32 black() { return new ColorU32(0, 0, 0, 0xFFFFFFFF); }
        @Contract(pure = true, value = "-> new")
        public static ColorU32 zero() { return new ColorU32(0, 0, 0, 0); }
        @Contract(pure = true, value = "-> new")
        public static ColorU32 one() { return new ColorU32(1, 1, 1, 1); }
    }

    record DepthStencil(float depth, int stencil) implements VulkanicClearValue { }

    @Contract(mutates = "param1")
    default void copyTo(VkClearValue target) {
        switch (this) {
            case ColorF32(float r, float g, float b, float a) -> target.color().float32(0, r).float32(1, g).float32(2, b).float32(3, a);
            case ColorI32(int r, int g, int b, int a) -> target.color().int32(0, r).int32(1, g).int32(2, b).int32(3, a);
            case ColorU32(int r, int g, int b, int a) -> target.color().uint32(0, r).uint32(1, g).uint32(2, b).uint32(3, a);
            case DepthStencil(float depth, int stencil) -> target.depthStencil().set(depth, stencil);
        }
    }
}
