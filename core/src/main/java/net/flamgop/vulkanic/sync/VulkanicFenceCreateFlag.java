package net.flamgop.vulkanic.sync;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.VK10;

public enum VulkanicFenceCreateFlag implements Bitmaskable<Integer> {
    SIGNALED(VK10.VK_FENCE_CREATE_SIGNALED_BIT)
    ;

    private final int flag;
    VulkanicFenceCreateFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
