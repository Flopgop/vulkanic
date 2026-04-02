package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.memory.VulkanicBuffer;
import net.flamgop.vulkanic.util.EnumLongBitset;

public record VulkanicBufferMemoryBarrier(
        EnumLongBitset<VulkanicAccessFlag> srcAccessMask,
        EnumLongBitset<VulkanicAccessFlag> dstAccessMask,
        int srcQueueFamilyIndex, int dstQueueFamilyIndex,
        VulkanicBuffer buffer, long offset, long size
) {
}
