package net.flamgop.vulkanic.pipeline;

import org.lwjgl.vulkan.*;

public enum VulkanicPipelineBindPoint {
    GRAPHICS(VK10.VK_PIPELINE_BIND_POINT_GRAPHICS),
    COMPUTE(VK10.VK_PIPELINE_BIND_POINT_COMPUTE),
    EXECUTION_GRAPH_AMDX(AMDXShaderEnqueue.VK_PIPELINE_BIND_POINT_EXECUTION_GRAPH_AMDX),
    RAY_TRACING_KHR(KHRRayTracingPipeline.VK_PIPELINE_BIND_POINT_RAY_TRACING_KHR),
    SUBPASS_SHADING_HUAWEI(HUAWEISubpassShading.VK_PIPELINE_BIND_POINT_SUBPASS_SHADING_HUAWEI),
    DATA_GRAPH_ARM(ARMDataGraph.VK_PIPELINE_BIND_POINT_DATA_GRAPH_ARM),
    RAY_TRACING_NV(NVRayTracing.VK_PIPELINE_BIND_POINT_RAY_TRACING_NV)

    ;

    private final int qualifier;
    VulkanicPipelineBindPoint(int qualifier) {
        this.qualifier = qualifier;
    }
    public int qualifier() {
        return qualifier;
    }
}
