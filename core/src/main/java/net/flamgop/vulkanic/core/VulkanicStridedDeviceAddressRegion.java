package net.flamgop.vulkanic.core;

import net.flamgop.vulkanic.memory.VulkanicDeviceSize;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkStridedDeviceAddressRegionKHR;

public record VulkanicStridedDeviceAddressRegion(
        long deviceAddress,
        @NotNull VulkanicDeviceSize stride,
        @NotNull VulkanicDeviceSize size
) {
    public VkStridedDeviceAddressRegionKHR build(MemoryStack stack) {
        return VkStridedDeviceAddressRegionKHR.calloc(stack)
                .deviceAddress(deviceAddress)
                .stride(stride.bytes())
                .size(size.bytes());
    }
}
