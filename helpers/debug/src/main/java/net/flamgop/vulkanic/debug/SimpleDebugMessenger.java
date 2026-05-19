package net.flamgop.vulkanic.debug;

import net.flamgop.vulkanic.core.debug.*;
import net.flamgop.vulkanic.util.EnumIntBitset;

import java.io.PrintStream;
import java.util.List;
import java.util.function.BiConsumer;

public class SimpleDebugMessenger implements VulkanicDebugMessenger {

    public static SimpleDebugMessenger stderr() {
        return new SimpleDebugMessenger(System.err);
    }

    public static SimpleDebugMessenger stdout() {
        return new SimpleDebugMessenger(System.out);
    }

    private final BiConsumer<EnumIntBitset<VulkanicDebugMessageSeverityFlag>, String> logAction;

    public SimpleDebugMessenger(PrintStream stream) {
        this((severity, message) -> stream.printf("[%s] %s\n", DebugHelpers.highestSeverity(severity), message.replace("\n", "\n\t")));
    }

    public SimpleDebugMessenger(BiConsumer<EnumIntBitset<VulkanicDebugMessageSeverityFlag>, String> logAction) {
        this.logAction = logAction;
    }

    private String formatMessage(EnumIntBitset<VulkanicDebugMessageSeverityFlag> severity,
                                 EnumIntBitset<VulkanicDebugMessageTypeFlag> type,
                                 VulkanicDebugCallbackData data) {
        StringBuilder sb = new StringBuilder();

        String sevStr = severity.toFriendlyString(VulkanicDebugMessageSeverityFlag.class);
        String typeStr = type.toFriendlyString(VulkanicDebugMessageTypeFlag.class);

        sb.append(String.format("Vulkan %s [%s] - %s (ID: 0x%08X)\n",
                sevStr.isEmpty() ? "UNKNOWN_SEVERITY" : sevStr,
                typeStr.isEmpty() ? "UNKNOWN_TYPE" : typeStr,
                data.messageIdName() != null ? data.messageIdName() : "Unnamed",
                data.messageIdNumber()));

        if (data.message() != null) {
            String cleanMsg = data.message().replace(" | ", "\n");

            cleanMsg.lines().forEach(line ->
                    sb.append("    ").append(line.trim()).append('\n')
            );
        }

        if (data.objects() != null && !data.objects().isEmpty()) {
            sb.append("    Objects:\n");
            for (int i = 0; i < data.objects().size(); i++) {
                VulkanicDebugObjectNameInfo obj = data.objects().get(i);
                sb.append(String.format("      [%d] %s, Handle: 0x%X, Name: '%s'\n",
                        i,
                        obj.objectType(),
                        obj.objectHandle(),
                        obj.objectName() == null ? "Unnamed" : obj.objectName()));
            }
        }

        formatLabels(sb, "Queue Labels", data.queueLabels());
        formatLabels(sb, "Command Buffer Labels", data.commandBufferLabels());

        return sb.toString().trim();
    }

    private void formatLabels(StringBuilder sb, String title, List<VulkanicDebugLabel> labels) {
        if (labels != null && !labels.isEmpty()) {
            sb.append("    ").append(title).append(":\n");
            for (VulkanicDebugLabel label : labels) {
                sb.append(String.format("      - %s\n", label.labelName()));
            }
        }
    }

    @Override
    public boolean message(EnumIntBitset<VulkanicDebugMessageSeverityFlag> severity, EnumIntBitset<VulkanicDebugMessageTypeFlag> type, VulkanicDebugCallbackData callbackData) {
        String formattedMessage = formatMessage(severity, type, callbackData);
        logAction.accept(severity, formattedMessage);
        return false;
    }
}
