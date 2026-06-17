package net.flamgop.vulkanic.pipeline.graphics.renderpass;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.VK12;

public enum VulkanicFramebufferCreateFlag implements Bitmaskable<Integer> {
    // VK 1.2 or VK_KHR_imageless_framebuffer
    IMAGELESS(VK12.VK_FRAMEBUFFER_CREATE_IMAGELESS_BIT),

    ;
    private final int flag;
    VulkanicFramebufferCreateFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
