package net.flamgop.vulkanic.memory;

import net.flamgop.vulkanic.util.EnumIntBitset;

public record VulkanicAllocationCreateInfo(
        EnumIntBitset<VulkanicAllocationCreateFlag> flags,
        VulkanicMemoryUsage memoryUsage,
        EnumIntBitset<VulkanicMemoryPropertyFlag> requiredFlags,
        EnumIntBitset<VulkanicMemoryPropertyFlag> preferredFlags,
        int memoryTypeBits
        // TODO: determine if pool, priority, and pUserData matter
) {
}
