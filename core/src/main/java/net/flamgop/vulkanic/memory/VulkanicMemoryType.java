package net.flamgop.vulkanic.memory;

import net.flamgop.vulkanic.util.EnumIntBitset;

public record VulkanicMemoryType(
        EnumIntBitset<VulkanicMemoryPropertyFlag> propertyFlags,
        int heapIndex
) {
}
