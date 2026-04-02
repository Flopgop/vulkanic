package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.memory.image.VulkanicImage;
import net.flamgop.vulkanic.memory.image.VulkanicImageLayout;
import net.flamgop.vulkanic.memory.image.VulkanicImageSubresourceRange;
import net.flamgop.vulkanic.util.EnumLongBitset;

public record VulkanicImageMemoryBarrier(
        EnumLongBitset<VulkanicAccessFlag> srcAccessMask,
        EnumLongBitset<VulkanicAccessFlag> dstAccessMask,
        VulkanicImageLayout oldLayout, VulkanicImageLayout newLayout,
        int srcQueueFamilyIndex, int dstQueueFamilyIndex,
        VulkanicImage image, VulkanicImageSubresourceRange subresourceRange
) {
}
