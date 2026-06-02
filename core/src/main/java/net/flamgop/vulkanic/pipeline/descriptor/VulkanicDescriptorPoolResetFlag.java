package net.flamgop.vulkanic.pipeline.descriptor;

import net.flamgop.vulkanic.util.Bitmaskable;

public enum VulkanicDescriptorPoolResetFlag implements Bitmaskable<Integer> {
    // reserved for future use -vk spec
    ;
    private final int flag;
    VulkanicDescriptorPoolResetFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
