package net.flamgop.vulkanic.memory.image;

import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.vulkan.VkImageSubresourceLayers;

public record VulkanicImageSubresourceLayers(EnumIntBitset<VulkanicImageAspectFlag> aspectMask, int mipLevel, int baseArrayLevel, int layerCount) {
    @Contract(mutates = "param1", value = "_ -> param1")
    public @NotNull VkImageSubresourceLayers get(@NotNull VkImageSubresourceLayers subresource) {
        return subresource.aspectMask(aspectMask.mask())
                .mipLevel(mipLevel)
                .baseArrayLayer(baseArrayLevel)
                .layerCount(layerCount);
    }
}
