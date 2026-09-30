package net.flamgop.vulkanic.core.fault;

public record VulkanicDeviceFaultVendorInfo(
        String description,
        long vendorFaultCode,
        long vendorFaultData
) {
}
