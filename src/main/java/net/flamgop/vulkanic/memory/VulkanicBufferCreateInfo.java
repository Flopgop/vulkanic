package net.flamgop.vulkanic.memory;

import net.flamgop.vulkanic.memory.image.VulkanicSharingMode;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;

public record VulkanicBufferCreateInfo(
        @NotNull VulkanicDeviceSize size,
        @NotNull EnumIntBitset<VulkanicBufferUsageFlag> usage,
        @NotNull VulkanicSharingMode sharingMode,
        int queueFamilyIndexCount, int... queueFamilyIndices
) {
}
