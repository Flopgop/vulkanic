package net.flamgop.vulkanic.memory.copy;

import net.flamgop.vulkanic.memory.VulkanicDeviceSize;
import org.jetbrains.annotations.NotNull;

public record VulkanicBufferCopy(
        @NotNull VulkanicDeviceSize srcOffset,
        @NotNull VulkanicDeviceSize dstOffset,
        @NotNull VulkanicDeviceSize size
) {
}
