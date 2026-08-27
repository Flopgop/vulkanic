package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.VK10;

public enum VulkanicCommandBufferUsageFlag implements Bitmaskable<Integer> {
    /// Signifies this command buffer will be allocated, submitted, and then destroyed (after the queue signals it is done.)
    ONE_TIME_SUBMIT(VK10.VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT),
    /// Signifies that this command buffer is a secondary command buffer which will draw into an active render pass on the parent primary command buffer.
    RENDER_PASS_CONTINUE(VK10.VK_COMMAND_BUFFER_USAGE_RENDER_PASS_CONTINUE_BIT),
    /// Allows this command buffer to be submitted or recorded concurrently while it is still in the pending state. This may be used for re-using secondary command buffers across multiple primaries, repeatedly executing the same secondary command buffer in the same primary, or submitting the same primary command buffer multiple times before it completes.
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
