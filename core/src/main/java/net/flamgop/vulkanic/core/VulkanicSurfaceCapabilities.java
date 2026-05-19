package net.flamgop.vulkanic.core;

import net.flamgop.vulkanic.memory.image.VulkanicImageUsageFlag;
import net.flamgop.vulkanic.swapchain.VulkanicCompositeAlphaFlag;
import net.flamgop.vulkanic.swapchain.VulkanicSurfaceTransformFlag;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.joml.Vector2ic;

public record VulkanicSurfaceCapabilities(
        int minImageCount, int maxImageCount,
        Vector2ic currentExtent, Vector2ic minImageExtent, Vector2ic maxImageExtent, int maxImageArrayLayers,
        EnumIntBitset<VulkanicSurfaceTransformFlag> supportedTransforms, VulkanicSurfaceTransformFlag currentTransform,
        EnumIntBitset<VulkanicCompositeAlphaFlag> supportedCompositeAlpha, EnumIntBitset<VulkanicImageUsageFlag> supportedUsageFlags
) {
}
