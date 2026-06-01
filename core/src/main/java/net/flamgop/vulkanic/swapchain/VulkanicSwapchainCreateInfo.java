package net.flamgop.vulkanic.swapchain;

import net.flamgop.vulkanic.memory.VulkanicFormat;
import net.flamgop.vulkanic.memory.image.VulkanicImageUsageFlag;
import net.flamgop.vulkanic.memory.image.VulkanicSharingMode;
import net.flamgop.vulkanic.surface.VulkanicSurface;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2ic;

public record VulkanicSwapchainCreateInfo(
        @NotNull EnumIntBitset<VulkanicSwapchainCreateFlag> flags,
        @NotNull VulkanicSurface surface, int minImageCount,
        @NotNull VulkanicFormat imageFormat, @NotNull VulkanicColorSpace imageColorSpace,
        @NotNull Vector2ic extent, int imageArrayLayers,
        @NotNull EnumIntBitset<VulkanicImageUsageFlag> imageUsage,
        @NotNull VulkanicSharingMode imageSharingMode,
        int @Nullable [] queueFamilyIndices,
        @NotNull VulkanicSurfaceTransformFlag preTransform, @NotNull VulkanicCompositeAlphaFlag compositeAlpha,
        @NotNull VulkanicPresentMode presentMode, boolean clipped,
        @Nullable VulkanicSwapchain oldSwapchain
) {
    public VulkanicSwapchainCreateInfo forSwapchain(VulkanicSwapchain oldSwapchain) {
        return new VulkanicSwapchainCreateInfo(
                flags, surface, minImageCount, imageFormat, imageColorSpace, extent, imageArrayLayers,
                imageUsage, imageSharingMode, queueFamilyIndices, preTransform, compositeAlpha, presentMode, clipped,
                oldSwapchain
        );
    }
}
