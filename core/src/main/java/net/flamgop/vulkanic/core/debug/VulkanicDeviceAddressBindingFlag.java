package net.flamgop.vulkanic.core.debug;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.EXTDeviceAddressBindingReport;

public enum VulkanicDeviceAddressBindingFlag implements Bitmaskable<Integer> {
    INTERNAL_OBJECT_EXT(EXTDeviceAddressBindingReport.VK_DEVICE_ADDRESS_BINDING_INTERNAL_OBJECT_BIT_EXT)

    ;
    private final int flag;
    VulkanicDeviceAddressBindingFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
