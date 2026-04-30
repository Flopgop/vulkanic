package net.flamgop.vulkanic.core;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.KHRInternallySynchronizedQueues;
import org.lwjgl.vulkan.VK11;

public enum VulkanicQueueCreateFlag implements Bitmaskable<Integer> {
    PROTECTED(VK11.VK_DEVICE_QUEUE_CREATE_PROTECTED_BIT),
    INTERNALLY_SYNCHRONIZED_KHR(KHRInternallySynchronizedQueues.VK_DEVICE_QUEUE_CREATE_INTERNALLY_SYNCHRONIZED_BIT_KHR)
    ;

    private final int flag;
    VulkanicQueueCreateFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
