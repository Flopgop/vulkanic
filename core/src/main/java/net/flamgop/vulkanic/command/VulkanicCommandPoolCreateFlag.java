package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VK11;

public enum VulkanicCommandPoolCreateFlag implements Bitmaskable<Integer> {
    /// Signifies this command pool will only exist for a very short time and may be deleted quickly.
    TRANSIENT(VK10.VK_COMMAND_POOL_CREATE_TRANSIENT_BIT),

    /// Allows command buffers allocated from this pool to be reset after use.
    RESET_COMMAND_BUFFER(VK10.VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT),

    /// Is "protected", meaning this must be submitted to a protected queue, can only write to protected memory, and cannot be readback by the CPU (which may also increase friction for debugging.)
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
