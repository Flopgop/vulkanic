package net.flamgop.vulkanic.memory.platform;

public sealed interface VulkanicImportInfo {
    VulkanicExternalMemoryHandleTypeFlag handleType();

    record Win32HandleKHR(
        VulkanicExternalMemoryHandleTypeFlag handleType,
        long handle,
        String name
    ) implements VulkanicImportInfo {}
    
    record FdKHR(
            VulkanicExternalMemoryHandleTypeFlag handleType,
            int fd
    ) implements VulkanicImportInfo {}

    record HostPointerEXT(
            VulkanicExternalMemoryHandleTypeFlag handleType,
            long pHostPointer
    ) implements VulkanicImportInfo {}

    record HardwareBufferANDROID(
            VulkanicExternalMemoryHandleTypeFlag handleType,
            long pBuffer
    ) implements VulkanicImportInfo {}

    record MetalHandleEXT(
            VulkanicExternalMemoryHandleTypeFlag handleType,
            long pHandle
    ) implements VulkanicImportInfo {
    }
}
