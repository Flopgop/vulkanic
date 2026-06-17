package net.flamgop.vulkanic.pipeline.graphics.renderpass;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.QCOMRenderPassTransform;
import org.lwjgl.vulkan.VALVEFragmentDensityMapLayered;

public enum VulkanicRenderPassCreateFlag implements Bitmaskable<Integer> {

    TRANSFORM_QCOM(QCOMRenderPassTransform.VK_RENDER_PASS_CREATE_TRANSFORM_BIT_QCOM),

    PER_LAYER_FRAGMENT_DENSITY_VALVE(VALVEFragmentDensityMapLayered.VK_RENDER_PASS_CREATE_PER_LAYER_FRAGMENT_DENSITY_BIT_VALVE),

    ;
    private final int flag;
    VulkanicRenderPassCreateFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
