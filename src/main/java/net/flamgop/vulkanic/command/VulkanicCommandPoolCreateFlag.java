package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VK11;

public enum VulkanicCommandPoolCreateFlag implements Bitmaskable<Integer> {
    TRANSIENT(VK10.VK_COMMAND_POOL_CREATE_TRANSIENT_BIT),
    RESET_COMMAND_BUFFER(VK10.VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT),

    PROTECTED(VK11.VK_COMMAND_POOL_CREATE_PROTECTED_BIT)
    ;
    private final int flag;
    VulkanicCommandPoolCreateFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
