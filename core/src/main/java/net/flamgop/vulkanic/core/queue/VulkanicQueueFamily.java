package net.flamgop.vulkanic.core.queue;

import net.flamgop.vulkanic.core.VulkanicDevice;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class VulkanicQueueFamily {

    private final int index;

    private final List<VulkanicQueue> queues;
    private final int queueCount;
    private final boolean isProtected;

    /// @see VulkanicDevice#queueFamily
    @ApiStatus.Internal
    public VulkanicQueueFamily(@NotNull VulkanicDevice device, int index, int queueCount, boolean isProtected) {
        this.index = index;
        this.queues = new ArrayList<>(queueCount);
        for (int i = 0; i < queueCount; i++) {
            queues.add(device.queue(this, i));
        }
        this.queueCount = queueCount;
        this.isProtected = isProtected;
    }

    /// @return this queue family's index
    public int index() {
        return index;
    }

    /// @return the cached queue at the given index
    public VulkanicQueue queue(int index) {
        return queues.get(index);
    }

    /// @return the amount of queues in this family
    public int count() {
        return queueCount;
    }

    /// @return whether this queue family is protected
    public boolean isProtected() {
        return isProtected;
    }
}
