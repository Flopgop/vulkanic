package net.flamgop.vulkanic.core.debug;

import net.flamgop.vulkanic.memory.VulkanicDeviceSize;
import net.flamgop.vulkanic.util.EnumIntBitset;

public record VulkanicDeviceAddressBindingCallbackData(
        EnumIntBitset<VulkanicDeviceAddressBindingFlag> flags,
        long baseAddress,
        VulkanicDeviceSize size,
        VulkanicDeviceAddressBindingType bindingType
) {
}
