package net.flamgop.vulkanic.core.debug;

import org.lwjgl.vulkan.EXTDeviceAddressBindingReport;

public enum VulkanicDeviceAddressBindingType {
    BIND(EXTDeviceAddressBindingReport.VK_DEVICE_ADDRESS_BINDING_TYPE_BIND_EXT),
    UNBIND(EXTDeviceAddressBindingReport.VK_DEVICE_ADDRESS_BINDING_TYPE_UNBIND_EXT),
    ;
    private final int qualifier;
    VulkanicDeviceAddressBindingType(int qualifier) {
        this.qualifier = qualifier;
    }

    public int qualifier() {
        return qualifier;
    }

    public static VulkanicDeviceAddressBindingType valueOf(int qualifier) {
        return switch (qualifier) {
            case EXTDeviceAddressBindingReport.VK_DEVICE_ADDRESS_BINDING_TYPE_BIND_EXT -> BIND;
            case EXTDeviceAddressBindingReport.VK_DEVICE_ADDRESS_BINDING_TYPE_UNBIND_EXT -> UNBIND;
            default -> throw new IllegalArgumentException("bad qualifier for dab binding type");
        };
    }
}
