package net.flamgop.vulkanic.memory.image;

import net.flamgop.vulkanic.util.EnumIntBitset;

public record VulkanicImageSubresourceLayers(EnumIntBitset<VulkanicImageAspectFlag> aspectMask, int mipLevel, int baseArrayLevel, int layerCount) {
}
