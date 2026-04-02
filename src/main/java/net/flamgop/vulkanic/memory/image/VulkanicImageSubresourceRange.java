package net.flamgop.vulkanic.memory.image;

import net.flamgop.vulkanic.util.EnumIntBitset;
import org.lwjgl.vulkan.VkImageSubresourceRange;

public record VulkanicImageSubresourceRange(EnumIntBitset<VulkanicImageAspectFlag> aspectMask, int baseMipLevel, int levelCount, int baseArrayLayer, int layerCount) {
    public void get(VkImageSubresourceRange subresourceRange) {
        subresourceRange
                .aspectMask(aspectMask.mask())
                .baseMipLevel(baseMipLevel)
                .levelCount(levelCount)
                .baseArrayLayer(baseArrayLayer)
                .layerCount(layerCount);
    }
}
