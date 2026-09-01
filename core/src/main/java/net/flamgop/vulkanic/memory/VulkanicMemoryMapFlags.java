package net.flamgop.vulkanic.memory;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.EXTMapMemoryPlaced;

public enum VulkanicMemoryMapFlags implements Bitmaskable<Integer> {
        PLACED_EXT(EXTMapMemoryPlaced.VK_MEMORY_MAP_PLACED_BIT_EXT)
    ;
    private final int flag;
    VulkanicMemoryMapFlags(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
