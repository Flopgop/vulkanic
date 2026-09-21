package net.flamgop.vulkanic.pipeline.graphics.renderpass;

import net.flamgop.vulkanic.command.VulkanicClearValue;
import net.flamgop.vulkanic.math.VulkanicRect2D;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record VulkanicRenderPassBeginInfo(
        @NotNull VulkanicRenderPass renderPass,
        @NotNull VulkanicFramebuffer framebuffer,
        @NotNull VulkanicRect2D renderArea,
        @NotNull List<VulkanicClearValue> clearValues
) {
}
