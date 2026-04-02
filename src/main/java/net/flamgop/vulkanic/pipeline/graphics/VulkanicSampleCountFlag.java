package net.flamgop.vulkanic.pipeline.graphics;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.VK10;

public enum VulkanicSampleCountFlag implements Bitmaskable<Integer> {
    COUNT_1_BIT(VK10.VK_SAMPLE_COUNT_1_BIT),
    COUNT_2_BIT(VK10.VK_SAMPLE_COUNT_2_BIT),
    COUNT_4_BIT(VK10.VK_SAMPLE_COUNT_4_BIT),
    COUNT_8_BIT(VK10.VK_SAMPLE_COUNT_8_BIT),
    COUNT_16_BIT(VK10.VK_SAMPLE_COUNT_16_BIT),
    COUNT_32_BIT(VK10.VK_SAMPLE_COUNT_32_BIT),
    COUNT_64_BIT(VK10.VK_SAMPLE_COUNT_64_BIT),
    ;

    private final int flag;
    VulkanicSampleCountFlag(int flag) {
        this.flag = flag;
    }
    @Override
    public Integer flag() {
        return flag;
    }
}
