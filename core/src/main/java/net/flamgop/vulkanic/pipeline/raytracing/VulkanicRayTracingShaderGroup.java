package net.flamgop.vulkanic.pipeline.raytracing;

// TODO: capture replay support
public record VulkanicRayTracingShaderGroup(
        VulkanicRayTracingShaderGroupType type,
        int generalShaderIndex,
        int closestHitShaderIndex,
        int anyHitShaderIndex,
        int intersectionShaderIndex
) {
}
