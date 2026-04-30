package net.flamgop.vulkanic.pipeline.graphics;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkPipelineViewportStateCreateInfo;
import org.lwjgl.vulkan.VkRect2D;
import org.lwjgl.vulkan.VkViewport;

import java.util.List;

public record VulkanicViewportState(
        List<VulkanicViewport> viewports,
        List<VulkanicRect2D> scissors
) {
    public @NotNull VkPipelineViewportStateCreateInfo build(MemoryStack stack) {
        VkViewport.Buffer viewports = VkViewport.calloc(this.viewports.size(), stack);
        for (int i = 0; i < this.viewports.size(); i++) {
            VulkanicViewport viewport = this.viewports.get(i);
            viewport.get(viewports.get(i));
        }
        VkRect2D.Buffer scissors = VkRect2D.calloc(this.scissors.size(), stack);
        for (int i = 0; i < this.scissors.size(); i++) {
            VulkanicRect2D scissor = this.scissors.get(i);
            scissor.get(scissors.get(i));
        }

        return VkPipelineViewportStateCreateInfo.calloc(stack)
                .sType$Default()
                .pViewports(viewports)
                .pScissors(scissors);
    }
}
