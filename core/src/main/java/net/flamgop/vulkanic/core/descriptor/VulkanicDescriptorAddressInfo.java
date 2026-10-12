package net.flamgop.vulkanic.core.descriptor;

import net.flamgop.vulkanic.memory.VulkanicDeviceSize;
import net.flamgop.vulkanic.memory.format.VulkanicFormat;
import org.jetbrains.annotations.NotNull;

public record VulkanicDescriptorAddressInfo(long deviceAddress, @NotNull VulkanicDeviceSize range, @NotNull VulkanicFormat format) {
}
