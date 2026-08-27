package net.flamgop.vulkanic.core;

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

    public int index() {
        return index;
    }

    public VulkanicQueue queue(int index) {
        return queues.get(index);
    }

    public int count() {
        return queueCount;
    }

    public boolean isProtected() {
        return isProtected;
    }
}
