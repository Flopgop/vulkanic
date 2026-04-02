package net.flamgop.vulkanic.pipeline.graphics;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.VK10;

public enum VulkanicColorComponentFlag implements Bitmaskable<Integer> {
    R(VK10.VK_COLOR_COMPONENT_R_BIT),
    G(VK10.VK_COLOR_COMPONENT_G_BIT),
    B(VK10.VK_COLOR_COMPONENT_B_BIT),
    A(VK10.VK_COLOR_COMPONENT_A_BIT),
    ;

    private final int flag;
    VulkanicColorComponentFlag(int flag) {
        this.flag = flag;
    }
    @Override
    public Integer flag() {
        return flag;
    }
}
