package net.flamgop.vulkanic.reflect.bindings;

import net.flamgop.vulkanic.util.Bitmaskable;

public enum SpvReflectResourceType implements Bitmaskable<Integer> {
    SPV_REFLECT_RESOURCE_FLAG_UNDEFINED(0x00000000),
    SPV_REFLECT_RESOURCE_FLAG_SAMPLER(0x00000001),
    SPV_REFLECT_RESOURCE_FLAG_CBV(0x00000002),
    SPV_REFLECT_RESOURCE_FLAG_SRV(0x00000004),
    SPV_REFLECT_RESOURCE_FLAG_UAV(0x00000008);

    private final Integer flag;

    SpvReflectResourceType(Integer flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
