package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.memory.image.VulkanicImageAspectFlag;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;

public record VulkanicClearAttachment(
        @NotNull EnumIntBitset<VulkanicImageAspectFlag> aspectMask,
        int colorAttachment,
        @NotNull VulkanicClearValue clearValue
) { }
