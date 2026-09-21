package net.flamgop.vulkanic.pipeline.graphics;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.VK10;

/// FRONT_AND_BACK is just FRONT | BACK
public enum VulkanicStencilFaceFlag implements Bitmaskable<Integer> {
    FRONT(VK10.VK_STENCIL_FACE_FRONT_BIT),
    BACK(VK10.VK_STENCIL_FACE_BACK_BIT),
    ;
    private final int flag;
    VulkanicStencilFaceFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
