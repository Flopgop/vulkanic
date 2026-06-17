package net.flamgop.vulkanic.pipeline.graphics.renderpass;

import net.flamgop.vulkanic.command.VulkanicAccessFlag;
import net.flamgop.vulkanic.command.VulkanicDependencyFlag;
import net.flamgop.vulkanic.pipeline.VulkanicPipelineStageFlag;
import net.flamgop.vulkanic.util.EnumIntBitset;
import net.flamgop.vulkanic.util.EnumLongBitset;
import org.jetbrains.annotations.NotNull;

public record VulkanicSubpassDependency(
        int srcSubpass,
        int dstSubpass,
        @NotNull EnumLongBitset<VulkanicPipelineStageFlag> srcStageMask,
        @NotNull EnumLongBitset<VulkanicPipelineStageFlag> dstStageMask,
        @NotNull EnumLongBitset<VulkanicAccessFlag> srcAccessMask,
        @NotNull EnumLongBitset<VulkanicAccessFlag> dstAccessMask,
        @NotNull EnumIntBitset<VulkanicDependencyFlag> dependencyFlags
) {
}
