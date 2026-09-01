package net.flamgop.vulkanic.memory;

import org.jetbrains.annotations.NotNull;

public record VulkanicBindBufferMemoryInfo(
        @NotNull VulkanicBuffer buffer,
        @NotNull VulkanicDeviceMemory memory,
        @NotNull VulkanicDeviceSize memoryOffset
) {
}
