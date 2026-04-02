package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.VK10;

public enum VulkanicCommandBufferUsageFlag implements Bitmaskable<Integer> {
    ONE_TIME_SUBMIT(VK10.VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT),
    RENDER_PASS_CONTINUE(VK10.VK_COMMAND_BUFFER_USAGE_RENDER_PASS_CONTINUE_BIT),
    SIMULTANEOUS_USE(VK10.VK_COMMAND_BUFFER_USAGE_SIMULTANEOUS_USE_BIT),
    ;

    private final int flag;
    VulkanicCommandBufferUsageFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
