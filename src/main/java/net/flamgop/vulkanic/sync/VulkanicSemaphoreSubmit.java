package net.flamgop.vulkanic.sync;

import net.flamgop.vulkanic.pipeline.VulkanicPipelineStageFlag;
import net.flamgop.vulkanic.util.EnumLongBitset;

public record VulkanicSemaphoreSubmit(
        VulkanicSemaphore semaphore,
        EnumLongBitset<VulkanicPipelineStageFlag> stageMask
) {
}
