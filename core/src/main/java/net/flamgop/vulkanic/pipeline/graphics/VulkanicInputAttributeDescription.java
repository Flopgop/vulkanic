package net.flamgop.vulkanic.pipeline.graphics;

import net.flamgop.vulkanic.memory.format.VulkanicFormat;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.vulkan.VkVertexInputAttributeDescription;

public record VulkanicInputAttributeDescription(VulkanicFormat format, int binding, int location, int offset) {
    @SuppressWarnings("UnusedReturnValue")
    public @NotNull VkVertexInputAttributeDescription get(VkVertexInputAttributeDescription struct) {
        return struct
                .binding(binding)
                .location(location)
                .offset(offset)
                .format(format.qualifier());
    }
}
