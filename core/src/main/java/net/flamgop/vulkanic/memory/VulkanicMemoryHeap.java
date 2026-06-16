package net.flamgop.vulkanic.memory;

import net.flamgop.vulkanic.util.EnumIntBitset;

public record VulkanicMemoryHeap(
        VulkanicDeviceSize size,
        EnumIntBitset<VulkanicMemoryHeapFlag> flags
) {
}
