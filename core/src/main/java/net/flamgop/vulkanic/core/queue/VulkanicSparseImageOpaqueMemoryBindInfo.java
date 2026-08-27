package net.flamgop.vulkanic.core.queue;

import net.flamgop.vulkanic.memory.image.VulkanicImage;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record VulkanicSparseImageOpaqueMemoryBindInfo(
        @NotNull VulkanicImage image,
        @NotNull List<VulkanicSparseMemoryBind> binds
) {
}
