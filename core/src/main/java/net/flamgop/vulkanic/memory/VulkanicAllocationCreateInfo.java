package net.flamgop.vulkanic.memory;

import net.flamgop.vulkanic.util.EnumIntBitset;

/// Note: Memory usage is effectively automatic in VMA based on requiredFlags and preferredFlags
public record VulkanicAllocationCreateInfo(
        EnumIntBitset<VulkanicMemoryPropertyFlag> requiredFlags,
        EnumIntBitset<VulkanicMemoryPropertyFlag> preferredFlags,
        int memoryTypeBits,
        float priority
        // TODO: determine if pool, priority, and pUserData matter
) {
}
