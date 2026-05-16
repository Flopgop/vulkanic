package net.flamgop.vulkanic.pipeline;

public sealed interface VulkanicPipeline extends AutoCloseable permits VulkanicComputePipeline, VulkanicGraphicsPipeline, VulkanicRayTracingPipeline {
    long handle();
    @Override
    void close();
}
