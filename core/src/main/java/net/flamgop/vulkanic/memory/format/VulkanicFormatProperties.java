package net.flamgop.vulkanic.memory.format;

import net.flamgop.vulkanic.util.EnumLongBitset;

public record VulkanicFormatProperties(
        EnumLongBitset<VulkanicFormatFeatureFlag> linearTilingFeatures,
        EnumLongBitset<VulkanicFormatFeatureFlag> optimalTilingFeatures,
        EnumLongBitset<VulkanicFormatFeatureFlag> bufferFeatures
) {
}
