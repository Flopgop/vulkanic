package net.flamgop.vulkanic.sync;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.VK10;

public enum VulkanicQueryControlFlag implements Bitmaskable<Integer> {
    PRECISE(VK10.VK_QUERY_CONTROL_PRECISE_BIT)

    ;
    private final int flag;
    VulkanicQueryControlFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
