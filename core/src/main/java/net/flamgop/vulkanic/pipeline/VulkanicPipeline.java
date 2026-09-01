package net.flamgop.vulkanic.pipeline;

import net.flamgop.vulkanic.core.VulkanicObject;
import net.flamgop.vulkanic.core.VulkanicObjectType;
import org.jspecify.annotations.NonNull;

public sealed interface VulkanicPipeline extends AutoCloseable, VulkanicObject.Opaque permits VulkanicComputePipeline, VulkanicGraphicsPipeline, VulkanicRayTracingPipeline {
    @Override long handle();
    @Override void close();
    @Override
    default @NonNull VulkanicObjectType objectType() {
        return VulkanicObjectType.PIPELINE;
    }
}
