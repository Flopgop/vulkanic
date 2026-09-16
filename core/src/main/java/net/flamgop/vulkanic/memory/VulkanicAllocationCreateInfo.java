package net.flamgop.vulkanic.memory;

import net.flamgop.vulkanic.util.EnumIntBitset;

/// Note: Memory usage is effectively automatic in VMA based on requiredFlags and preferredFlags
/// @apiNote A general standard for `createBuffer` and `createImage` methods is that a `memoryTypeBits` of 0 should be interpreted as "autodetect"
public record VulkanicAllocationCreateInfo(
        EnumIntBitset<VulkanicMemoryPropertyFlag> requiredFlags,
        EnumIntBitset<VulkanicMemoryPropertyFlag> preferredFlags,
        int memoryTypeBits,
        float priority
        // TODO: determine if pool and pUserData matter
) {
    public VulkanicAllocationCreateInfo(
            EnumIntBitset<VulkanicMemoryPropertyFlag> requiredFlags,
            EnumIntBitset<VulkanicMemoryPropertyFlag> preferredFlags,
            float priority
    ) {
        this(requiredFlags, preferredFlags, 0, priority);
    }
}
