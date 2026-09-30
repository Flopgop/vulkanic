package net.flamgop.vulkanic.core.fault;

import java.lang.foreign.MemorySegment;
import java.util.List;

public record VulkanicDeviceFaultInfo(
        String description,
        List<VulkanicDeviceFaultAddressInfo> addressInfos,
        List<VulkanicDeviceFaultVendorInfo> vendorInfos,
        MemorySegment vendorBinaryData
) {
}
