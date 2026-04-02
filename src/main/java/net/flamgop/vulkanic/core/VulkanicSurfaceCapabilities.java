package net.flamgop.vulkanic.core;

import net.flamgop.vulkanic.memory.image.VulkanicImageUsageFlag;
import net.flamgop.vulkanic.swapchain.VulkanicCompositeAlphaFlag;
import net.flamgop.vulkanic.swapchain.VulkanicSurfaceTransformFlag;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.joml.Vector2i;

public record VulkanicSurfaceCapabilities(
        int minImageCount, int maxImageCount,
        Vector2i currentExtent, Vector2i minImageExtent, Vector2i maxImageExtent, int maxImageArrayLayers,
        EnumIntBitset<VulkanicSurfaceTransformFlag> supportedTransforms, VulkanicSurfaceTransformFlag currentTransform,
        EnumIntBitset<VulkanicCompositeAlphaFlag> supportedCompositeAlpha, EnumIntBitset<VulkanicImageUsageFlag> supportedUsageFlags
) {
}
