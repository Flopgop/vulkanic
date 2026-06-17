package net.flamgop.vulkanic.pipeline.graphics.renderpass;

import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;

public record VulkanicSubpassDescription(
        @NotNull EnumIntBitset<VulkanicSubpassDescriptionFlag> flags,

        ) {
}
