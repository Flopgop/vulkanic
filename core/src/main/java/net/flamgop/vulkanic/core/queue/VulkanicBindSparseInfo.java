package net.flamgop.vulkanic.core.queue;

import net.flamgop.vulkanic.sync.VulkanicSemaphore;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record VulkanicBindSparseInfo(
        @NotNull List<VulkanicSemaphore> waitSemaphores,
        @NotNull List<VulkanicSparseBufferMemoryBindInfo> bufferBinds,
        @NotNull List<VulkanicSparseImageOpaqueMemoryBindInfo> imageOpaqueBinds,
        @NotNull List<VulkanicSparseImageMemoryBindInfo> imageBinds,
        @NotNull List<VulkanicSemaphore> signalSemaphores
) {
}
