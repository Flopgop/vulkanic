package net.flamgop.vulkanic.core;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VulkanicQueueFamily {

    private final VulkanicDevice device;
    private final int index;

    private final List<VulkanicQueue> queues;
    private final int queueCount;

    @ApiStatus.Internal
    public VulkanicQueueFamily(@NotNull VulkanicDevice device, int index, int queueCount) {
        this.device = device;
        this.index = index;
        this.queues = new ArrayList<>(queueCount);
        for (int i = 0; i < queueCount; i++) {
            queues.add(device.queue(index, i));
        }
        this.queueCount = queueCount;
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
}
