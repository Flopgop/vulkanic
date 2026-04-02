package net.flamgop.vulkanic.pipeline.graphics;

import net.flamgop.vulkanic.memory.VulkanicFormat;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkVertexInputAttributeDescription;

public record VulkanicInputAttributeDescription(VulkanicFormat format, int binding, int location, int offset) {
    public @NotNull VkVertexInputAttributeDescription get(VkVertexInputAttributeDescription struct) {
        return struct
                .binding(binding)
                .location(location)
                .offset(offset)
                .format(format.qualifier());
    }
}
