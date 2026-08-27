package net.flamgop.vulkanic.core.queue;

import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;

/// Info describing a queue family
/// @param queuePriorities The number of queue priorities directly corresponds to the number of queues that will be created for this queue family
public record VulkanicQueueInfo(@NotNull EnumIntBitset<VulkanicQueueCreateFlag> createFlags, int queueFamilyIndex, float... queuePriorities) {
    public int queueCount() {
        return queuePriorities.length;
    }
}
