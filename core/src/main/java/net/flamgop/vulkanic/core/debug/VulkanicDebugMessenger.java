package net.flamgop.vulkanic.core.debug;

import net.flamgop.vulkanic.util.EnumIntBitset;

public interface VulkanicDebugMessenger {
    /**
     * @param severity message severity
     * @param type message type
     * @param callbackData related message data
     * @return Applications should always return `false`. `true` is reserved for use in layer development.
     */
    @SuppressWarnings("SameReturnValue")
    boolean message(EnumIntBitset<VulkanicDebugMessageSeverityFlag> severity, EnumIntBitset<VulkanicDebugMessageTypeFlag> type, VulkanicDebugCallbackData callbackData);
}
