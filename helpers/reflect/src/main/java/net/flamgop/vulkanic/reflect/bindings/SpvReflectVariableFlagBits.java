package net.flamgop.vulkanic.reflect.bindings;

import net.flamgop.vulkanic.util.Bitmaskable;

public enum SpvReflectVariableFlagBits implements Bitmaskable<Integer> {
    SPV_REFLECT_VARIABLE_FLAGS_NONE(0x00000000),
    SPV_REFLECT_VARIABLE_FLAGS_UNUSED(0x00000001),
    SPV_REFLECT_VARIABLE_FLAGS_PHYSICAL_POINTER_COPY(0x00000002);

    private final Integer flag;

    SpvReflectVariableFlagBits(Integer flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
