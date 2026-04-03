package net.flamgop.vulkanic.pipeline;

import net.flamgop.vulkanic.memory.VulkanicDeviceSize;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;

public record VulkanicPushConstantRange(@NotNull EnumIntBitset<VulkanicShaderStage> stageFlags, int offset, @NotNull VulkanicDeviceSize size) {
}
