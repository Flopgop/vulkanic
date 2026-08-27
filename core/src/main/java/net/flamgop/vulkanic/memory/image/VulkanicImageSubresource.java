package net.flamgop.vulkanic.memory.image;

import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;

public record VulkanicImageSubresource(
        @NotNull EnumIntBitset<VulkanicImageAspectFlag> aspectMask,
        int mipLevel,
        int arrayLayer
) {
}
