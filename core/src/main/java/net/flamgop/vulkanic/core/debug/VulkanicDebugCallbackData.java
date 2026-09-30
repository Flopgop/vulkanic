package net.flamgop.vulkanic.core.debug;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record VulkanicDebugCallbackData(
        @Nullable String messageIdName,
        int messageIdNumber,
        @NotNull String message,
        @NotNull List<VulkanicDebugLabel> queueLabels,
        @NotNull List<VulkanicDebugLabel> commandBufferLabels,
        @NotNull List<VulkanicDebugObjectNameInfo> objects,
        @Nullable VulkanicDeviceAddressBindingCallbackData deviceAddressBindingCallbackData
) {
}
