package net.flamgop.vulkanic.pipeline.graphics;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.VK10;

public enum VulkanicCullModeFlag implements Bitmaskable<Integer> {
    NONE(VK10.VK_CULL_MODE_NONE),
    FRONT(VK10.VK_CULL_MODE_FRONT_BIT),
    BACK(VK10.VK_CULL_MODE_BACK_BIT),
    FRONT_AND_BACK(VK10.VK_CULL_MODE_FRONT_AND_BACK),
    ;

    private final int flag;
    VulkanicCullModeFlag(int flag) {
        this.flag = flag;
    }
    @Override
    public Integer flag() {
        return flag;
    }
}
