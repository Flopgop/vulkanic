package net.flamgop.vulkanic.core;

import net.flamgop.vulkanic.util.EnumIntBitset;

public record VulkanicQueueInfo(EnumIntBitset<VulkanicQueueCreateFlag> createFlags, int queueFamilyIndex, float... queuePriorities) {
    public int queueCount() {
        return queuePriorities.length;
    }
}
