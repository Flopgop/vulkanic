package net.flamgop.vulkanic.memory.image;

import net.flamgop.vulkanic.math.Int3;
import net.flamgop.vulkanic.memory.format.VulkanicFormat;
import net.flamgop.vulkanic.pipeline.graphics.VulkanicSampleCountFlag;
import net.flamgop.vulkanic.util.EnumIntBitset;

public record VulkanicImageCreateInfo(
        EnumIntBitset<VulkanicImageCreateFlag> flags,
        VulkanicImageType imageType,
        VulkanicFormat format,
        Int3 extent,
        int mipLevels,
        int arrayLayers,
        VulkanicSampleCountFlag samples,
        VulkanicImageTiling tiling,
        EnumIntBitset<VulkanicImageUsageFlag> usage,
        VulkanicSharingMode sharingMode,
        VulkanicImageLayout initialLayout,
        int... queueFamilyIndices
) {
}
