package net.flamgop.vulkanic.core.fault;

import net.flamgop.vulkanic.memory.VulkanicDeviceSize;

public record VulkanicDeviceFaultAddressInfo(
        VulkanicDeviceFaultAddressType type,
        long reportedAddress,
        VulkanicDeviceSize addressPrecision
) {
}
