package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.pipeline.VulkanicPipelineStageFlag;
import net.flamgop.vulkanic.util.EnumLongBitset;

public record VulkanicMemoryBarrier(
        EnumLongBitset<VulkanicPipelineStageFlag> srcStageMask,
        EnumLongBitset<VulkanicAccessFlag> srcAccessMask,
        EnumLongBitset<VulkanicPipelineStageFlag> dstStageMask,
        EnumLongBitset<VulkanicAccessFlag> dstAccessMask
) {
}
