package net.flamgop.vulkanic.surface;

import net.flamgop.vulkanic.math.Int2;
import net.flamgop.vulkanic.memory.image.VulkanicImageUsageFlag;
import net.flamgop.vulkanic.swapchain.VulkanicCompositeAlphaFlag;
import net.flamgop.vulkanic.swapchain.VulkanicSurfaceTransformFlag;
import net.flamgop.vulkanic.util.EnumIntBitset;

public record VulkanicSurfaceCapabilities(
        int minImageCount, int maxImageCount,
        Int2 currentExtent, Int2 minImageExtent, Int2 maxImageExtent, int maxImageArrayLayers,
        EnumIntBitset<VulkanicSurfaceTransformFlag> supportedTransforms, VulkanicSurfaceTransformFlag currentTransform,
        EnumIntBitset<VulkanicCompositeAlphaFlag> supportedCompositeAlpha, EnumIntBitset<VulkanicImageUsageFlag> supportedUsageFlags
) {
}
