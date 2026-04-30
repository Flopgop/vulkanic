package net.flamgop.vulkanic.pipeline.graphics;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkPipelineInputAssemblyStateCreateInfo;

public record VulkanicInputAssemblyState(VulkanicPrimitiveTopology topology, boolean primitiveRestartEnable) {
    public @NotNull VkPipelineInputAssemblyStateCreateInfo build(MemoryStack stack) {
        return VkPipelineInputAssemblyStateCreateInfo.calloc(stack)
                .sType$Default()
                .topology(topology.qualifier())
                .primitiveRestartEnable(primitiveRestartEnable);
    }
}
