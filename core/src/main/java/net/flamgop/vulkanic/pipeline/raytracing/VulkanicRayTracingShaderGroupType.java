package net.flamgop.vulkanic.pipeline.raytracing;

import org.lwjgl.vulkan.KHRRayTracingPipeline;

public enum VulkanicRayTracingShaderGroupType {
    GENERAL_KHR(KHRRayTracingPipeline.VK_RAY_TRACING_SHADER_GROUP_TYPE_GENERAL_KHR),
    TRIANGLES_HIT_GROUP_KHR(KHRRayTracingPipeline.VK_RAY_TRACING_SHADER_GROUP_TYPE_TRIANGLES_HIT_GROUP_KHR),
    PROCEDURAL_HIT_GROUP_KHR(KHRRayTracingPipeline.VK_RAY_TRACING_SHADER_GROUP_TYPE_PROCEDURAL_HIT_GROUP_KHR),

    // all the NV types are just aliases to above
    ;
    private final int qualifier;
    VulkanicRayTracingShaderGroupType(int qualifier) {
        this.qualifier = qualifier;
    }

    public int qualifier() {
        return qualifier;
    }
}
