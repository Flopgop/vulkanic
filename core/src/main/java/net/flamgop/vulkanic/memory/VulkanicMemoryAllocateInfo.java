package net.flamgop.vulkanic.memory;

import net.flamgop.vulkanic.memory.platform.VulkanicImportInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record VulkanicMemoryAllocateInfo(
        @NotNull VulkanicDeviceSize allocationSize,
        int memoryTypeIndex,
        @Nullable VulkanicImportInfo importInfo
) {
}
