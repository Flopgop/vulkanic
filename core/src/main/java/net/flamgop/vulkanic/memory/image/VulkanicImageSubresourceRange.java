package net.flamgop.vulkanic.memory.image;

import net.flamgop.vulkanic.util.EnumIntBitset;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkImageSubresourceRange;

public record VulkanicImageSubresourceRange(EnumIntBitset<VulkanicImageAspectFlag> aspectMask, int baseMipLevel, int levelCount, int baseArrayLayer, int layerCount) {
    public static VulkanicImageSubresourceRange wholeRange(EnumIntBitset<VulkanicImageAspectFlag> aspectMask) {
        return new VulkanicImageSubresourceRange(
                aspectMask,
                0, VK10.VK_REMAINING_MIP_LEVELS,
                0, VK10.VK_REMAINING_ARRAY_LAYERS
        );
    }

    public void get(VkImageSubresourceRange subresourceRange) {
        subresourceRange
                .aspectMask(aspectMask.mask())
                .baseMipLevel(baseMipLevel)
                .levelCount(levelCount)
                .baseArrayLayer(baseArrayLayer)
                .layerCount(layerCount);
    }
}
