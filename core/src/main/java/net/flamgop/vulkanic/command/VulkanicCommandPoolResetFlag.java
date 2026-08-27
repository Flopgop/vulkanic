package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.VK10;

public enum VulkanicCommandPoolResetFlag implements Bitmaskable<Integer> {
    /// Makes the driver automatically release any resources allocated for this command pool.
    /// Ideally used for things like transient command pools, where you aren't frequently reusing the buffers.
    RESET_RELEASE_RESOURCES(VK10.VK_COMMAND_POOL_RESET_RELEASE_RESOURCES_BIT)
    ;

    private final int flag;
    @SuppressWarnings("SameParameterValue")
    VulkanicCommandPoolResetFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
