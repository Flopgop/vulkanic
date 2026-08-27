package net.flamgop.vulkanic.core.queue;

import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3ic;

/// Properties of a given queue family.
public record VulkanicQueueFamilyProperties(
        @NotNull EnumIntBitset<VulkanicQueueFlag> queueFlags, int queueCount,
        int timestampValidBits, @NotNull Vector3ic minImageTransferGranularity, int index
) {
}
