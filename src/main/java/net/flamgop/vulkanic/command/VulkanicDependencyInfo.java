package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record VulkanicDependencyInfo(
        @NotNull EnumIntBitset<VulkanicDependencyFlag> dependencyFlags,
        @NotNull List<VulkanicMemoryBarrier> memoryBarriers,
        @NotNull List<VulkanicBufferMemoryBarrier> bufferMemoryBarriers,
        @NotNull List<VulkanicImageMemoryBarrier> imageMemoryBarriers
        ) {
}
