package net.flamgop.vulkanic.sync;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.KHRMaintenance9;

public enum VulkanicQueryPoolCreateFlag implements Bitmaskable<Integer> {

    /// Specifies that queries are reset on creation and do not need to be reset before first use.
    RESET_KHR(KHRMaintenance9.VK_QUERY_POOL_CREATE_RESET_BIT_KHR)

    ;
    private final int flag;

    @SuppressWarnings("SameParameterValue")
    VulkanicQueryPoolCreateFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
