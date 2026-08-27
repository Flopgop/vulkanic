package net.flamgop.vulkanic.core.queue;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.VK10;

public enum VulkanicSparseMemoryBindFlag implements Bitmaskable<Integer> {
    METADATA(VK10.VK_SPARSE_MEMORY_BIND_METADATA_BIT)
    ;

    private final int flag;
    VulkanicSparseMemoryBindFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
