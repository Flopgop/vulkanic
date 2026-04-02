package net.flamgop.vulkanic.core;

import net.flamgop.vulkanic.util.EnumIntBitset;
import org.joml.Vector3i;

public record VulkanicQueueFamilyProperties(
        EnumIntBitset<VulkanicQueueFlag> queueFlags, int queueCount,
        int timestampValidBits, Vector3i minImageTransferGranularity
) {
}
