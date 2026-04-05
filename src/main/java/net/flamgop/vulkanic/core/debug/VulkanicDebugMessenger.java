package net.flamgop.vulkanic.core.debug;

import net.flamgop.vulkanic.util.EnumIntBitset;

public interface VulkanicDebugMessenger {
    boolean message(EnumIntBitset<VulkanicDebugMessageSeverityFlag> severity, EnumIntBitset<VulkanicDebugMessageTypeFlag> type, VulkanicDebugCallbackData callbackData);
}
