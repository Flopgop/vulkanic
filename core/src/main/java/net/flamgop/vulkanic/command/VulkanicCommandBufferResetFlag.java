package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.VK10;

public enum VulkanicCommandBufferResetFlag implements Bitmaskable<Integer> {
    RESET_RELEASE_RESOURCES(VK10.VK_COMMAND_BUFFER_RESET_RELEASE_RESOURCES_BIT)
    ;

    private final int flag;
    @SuppressWarnings("SameParameterValue")
    VulkanicCommandBufferResetFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
