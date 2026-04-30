package net.flamgop.vulkanic.pipeline.graphics;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkPipelineVertexInputStateCreateInfo;
import org.lwjgl.vulkan.VkVertexInputAttributeDescription;
import org.lwjgl.vulkan.VkVertexInputBindingDescription;

import java.util.List;

public record VulkanicVertexInputState(
        List<VulkanicInputAttributeDescription> attributes,
        List<VulkanicInputBindingDescription> bindings
) {
    public @NotNull VkPipelineVertexInputStateCreateInfo build(@NotNull MemoryStack stack) {
        VkVertexInputAttributeDescription.Buffer attributes = VkVertexInputAttributeDescription.calloc(this.attributes.size(), stack);
        for (int i = 0; i < this.attributes.size(); i++) {
            VulkanicInputAttributeDescription attribute = this.attributes.get(i);
            attribute.get(attributes.get(i));
        }
        VkVertexInputBindingDescription.Buffer bindings = VkVertexInputBindingDescription.calloc(this.bindings.size(), stack);
        for (int i = 0; i < this.bindings.size(); i++) {
            VulkanicInputBindingDescription binding = this.bindings.get(i);
            binding.get(bindings.get(i));
        }

        return VkPipelineVertexInputStateCreateInfo.calloc(stack)
                .sType$Default()
                .pVertexAttributeDescriptions(attributes)
                .pVertexBindingDescriptions(bindings);
    }
}
