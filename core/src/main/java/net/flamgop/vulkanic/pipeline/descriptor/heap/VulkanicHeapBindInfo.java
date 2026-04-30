package net.flamgop.vulkanic.pipeline.descriptor.heap;

import net.flamgop.vulkanic.memory.VulkanicDeviceSize;
import org.jetbrains.annotations.NotNull;

public record VulkanicHeapBindInfo(@NotNull VulkanicDeviceAddressRange heapRange, @NotNull VulkanicDeviceSize reservedRangeOffset, @NotNull VulkanicDeviceSize reservedRangeSize) {
}
