package net.flamgop.vulkanic.pipeline.raytracing;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkRayTracingPipelineInterfaceCreateInfoKHR;

public record VulkanicRayTracingPipelineInterfaceInfo(
        int maxPipelineRayPayloadSize,
        int maxPipelineRayHitAttributeSize
) {
    public VkRayTracingPipelineInterfaceCreateInfoKHR build(MemoryStack stack) {
        return VkRayTracingPipelineInterfaceCreateInfoKHR.calloc(stack)
                .sType$Default()
                .maxPipelineRayPayloadSize(maxPipelineRayPayloadSize)
                .maxPipelineRayHitAttributeSize(maxPipelineRayHitAttributeSize);
    }
}
