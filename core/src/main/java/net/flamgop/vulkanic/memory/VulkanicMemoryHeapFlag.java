package net.flamgop.vulkanic.memory;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.QCOMTileMemoryHeap;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VK11;

public enum VulkanicMemoryHeapFlag implements Bitmaskable<Integer> {
    DEVICE_LOCAL(VK10.VK_MEMORY_HEAP_DEVICE_LOCAL_BIT),

    MULTI_INSTANCE(VK11.VK_MEMORY_HEAP_MULTI_INSTANCE_BIT),

    TILE_MEMORY_QCOM(QCOMTileMemoryHeap.VK_MEMORY_HEAP_TILE_MEMORY_BIT_QCOM),

    // VK_KHR_device_group_creation
    MULTI_INSTANCE_KHR(MULTI_INSTANCE)
    ;

    private final int flag;
    VulkanicMemoryHeapFlag(VulkanicMemoryHeapFlag alias) {
        this.flag = alias.flag;
    }
    VulkanicMemoryHeapFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
