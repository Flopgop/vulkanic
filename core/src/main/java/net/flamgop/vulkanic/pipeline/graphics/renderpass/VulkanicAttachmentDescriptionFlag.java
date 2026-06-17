package net.flamgop.vulkanic.pipeline.graphics.renderpass;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.KHRMaintenance10;
import org.lwjgl.vulkan.VK10;

public enum VulkanicAttachmentDescriptionFlag implements Bitmaskable<Integer> {
    MAY_ALIAS(VK10.VK_ATTACHMENT_DESCRIPTION_MAY_ALIAS_BIT),

    RESOLVE_SKIP_TRANSFER_FUNCTION_KHR(KHRMaintenance10.VK_ATTACHMENT_DESCRIPTION_RESOLVE_SKIP_TRANSFER_FUNCTION_BIT_KHR),
    RESOLVE_ENABLE_TRANSFER_FUNCTION_KHR(KHRMaintenance10.VK_ATTACHMENT_DESCRIPTION_RESOLVE_ENABLE_TRANSFER_FUNCTION_BIT_KHR),
    ;
    private final int flag;
    VulkanicAttachmentDescriptionFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
