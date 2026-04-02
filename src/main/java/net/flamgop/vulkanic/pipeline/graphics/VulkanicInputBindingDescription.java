package net.flamgop.vulkanic.pipeline.graphics;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.vulkan.VkVertexInputBindingDescription;

public record VulkanicInputBindingDescription(int binding, int stride, VulkanicVertexInputRate inputRate) {
    public @NotNull VkVertexInputBindingDescription get(VkVertexInputBindingDescription struct) {
        return struct
                .binding(binding)
                .stride(stride)
                .inputRate(inputRate.qualifier());
    }
}
