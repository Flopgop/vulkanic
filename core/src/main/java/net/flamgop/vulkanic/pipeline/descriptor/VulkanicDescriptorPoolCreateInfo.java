package net.flamgop.vulkanic.pipeline.descriptor;

import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record VulkanicDescriptorPoolCreateInfo(
        @NotNull EnumIntBitset<VulkanicDescriptorPoolCreateFlag> flags,
        int maxSets,
        @NotNull List<VulkanicDescriptorPoolSize> poolSizes
) {
}
