package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record VulkanicCommandBufferBeginInfo(
        @NotNull EnumIntBitset<VulkanicCommandBufferUsageFlag> flags,
        @Nullable VulkanicCommandBufferInheritanceInfo inheritanceInfo
) {
    public VulkanicCommandBufferBeginInfo() {
        this(EnumIntBitset.empty(), null);
    }
}
