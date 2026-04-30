package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.memory.image.VulkanicImageLayout;
import net.flamgop.vulkanic.memory.image.VulkanicImageView;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record VulkanicRenderingAttachmentInfo(
        @NotNull VulkanicImageView imageView,
        @NotNull VulkanicImageLayout imageLayout,
        @Nullable ResolveInfo resolveInfo,
        @NotNull VulkanicAttachmentLoadOp loadOp,
        @NotNull VulkanicAttachmentStoreOp storeOp,
        @NotNull VulkanicClearValue clearValue
) {
    public record ResolveInfo(
                @NotNull EnumIntBitset<VulkanicResolveModeFlag> mode,
                @NotNull VulkanicImageView imageView,
                @NotNull VulkanicImageLayout imageLayout
    ) {
        public ResolveInfo {
            if (mode.none()) throw new IllegalArgumentException("If ResolveInfo is not null, ResolveInfo#mode must not be NONE!");
        }
    }
}
