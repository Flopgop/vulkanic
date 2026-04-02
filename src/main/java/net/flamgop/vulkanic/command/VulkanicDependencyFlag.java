package net.flamgop.vulkanic.command;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.*;

public enum VulkanicDependencyFlag implements Bitmaskable<Integer> {
    BY_REGION(VK10.VK_DEPENDENCY_BY_REGION_BIT),

    DEVICE_GROUP(VK11.VK_DEPENDENCY_DEVICE_GROUP_BIT),
    VIEW_LOCAL(VK11.VK_DEPENDENCY_VIEW_LOCAL_BIT),

    FEEDBACK_LOOP_EXT(EXTAttachmentFeedbackLoopLayout.VK_DEPENDENCY_FEEDBACK_LOOP_BIT_EXT),

    QUEUE_FAMILY_OWNERSHIP_TRANSFER_USE_ALL_STAGE_KHR(KHRMaintenance8.VK_DEPENDENCY_QUEUE_FAMILY_OWNERSHIP_TRANSFER_USE_ALL_STAGES_BIT_KHR),

    ASYMMETRIC_EVENT_KHR(KHRMaintenance9.VK_DEPENDENCY_ASYMMETRIC_EVENT_BIT_KHR),
    ;

    private final int flag;

    VulkanicDependencyFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
