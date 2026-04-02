package net.flamgop.vulkanic.pipeline;

import net.flamgop.vulkanic.util.EnumIntBitset;

public record VulkanicPushConstantRange(EnumIntBitset<VulkanicShaderStage> stageFlags, int offset, int size) {
}
