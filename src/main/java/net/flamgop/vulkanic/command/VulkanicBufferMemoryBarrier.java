package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.memory.VulkanicBuffer;
import net.flamgop.vulkanic.pipeline.VulkanicPipelineStageFlag;
import net.flamgop.vulkanic.util.EnumLongBitset;

public record VulkanicBufferMemoryBarrier(
        EnumLongBitset<VulkanicPipelineStageFlag> srcStageMask,
        EnumLongBitset<VulkanicAccessFlag> srcAccessMask,
        EnumLongBitset<VulkanicPipelineStageFlag> dstStageMask,
        EnumLongBitset<VulkanicAccessFlag> dstAccessMask,
        int srcQueueFamilyIndex, int dstQueueFamilyIndex,
        VulkanicBuffer buffer, long offset, long size
) {
}
