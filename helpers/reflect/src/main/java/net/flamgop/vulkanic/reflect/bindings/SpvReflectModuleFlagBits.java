package net.flamgop.vulkanic.reflect.bindings;

import net.flamgop.vulkanic.util.Bitmaskable;

public enum SpvReflectModuleFlagBits implements Bitmaskable<Integer> {
    SPV_REFLECT_MODULE_FLAG_NONE(0x00000000),
    SPV_REFLECT_MODULE_FLAG_NO_COPY(0x00000001);

    private final Integer flag;

    SpvReflectModuleFlagBits(Integer flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
