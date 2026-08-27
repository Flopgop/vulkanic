package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/// @param flags see [VulkanicCommandBufferUsageFlag]
/// @param inheritanceInfo see [VulkanicCommandBufferInheritanceInfo]
public record VulkanicCommandBufferBeginInfo(
        @NotNull EnumIntBitset<VulkanicCommandBufferUsageFlag> flags,
        @Nullable VulkanicCommandBufferInheritanceInfo inheritanceInfo
) {
    public VulkanicCommandBufferBeginInfo() {
        this(EnumIntBitset.empty(), null);
    }
}
