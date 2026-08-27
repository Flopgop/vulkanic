package net.flamgop.vulkanic.core.queue;

import net.flamgop.vulkanic.memory.VulkanicBuffer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record VulkanicSparseBufferMemoryBindInfo(
        @NotNull VulkanicBuffer buffer,
        @NotNull List<VulkanicSparseMemoryBind> binds
) {
}
