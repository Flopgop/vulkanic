package net.flamgop.vulkanic.swapchain;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.KHRSurface;

public enum VulkanicCompositeAlphaFlag implements Bitmaskable<Integer> {
    OPAQUE_KHR(KHRSurface.VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR),
    PRE_MULTIPLIED_KHR(KHRSurface.VK_COMPOSITE_ALPHA_PRE_MULTIPLIED_BIT_KHR),
    POST_MULTIPLIED_KHR(KHRSurface.VK_COMPOSITE_ALPHA_POST_MULTIPLIED_BIT_KHR),
    INHERIT_KHR(KHRSurface.VK_COMPOSITE_ALPHA_INHERIT_BIT_KHR),
    ;

    private final int flag;
    VulkanicCompositeAlphaFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
