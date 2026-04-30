package net.flamgop.vulkanic.memory.image;

import net.flamgop.vulkanic.memory.VulkanicFormat;
import net.flamgop.vulkanic.pipeline.graphics.VulkanicSampleCountFlag;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.joml.Vector3i;

public record VulkanicImageCreateInfo(EnumIntBitset<VulkanicImageCreateFlag> flags,
                                      VulkanicImageType imageType,
                                      VulkanicFormat format,
                                      Vector3i extent,
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
