package net.flamgop.vulkanic.core.debug;

import org.joml.Vector4f;

public record VulkanicDebugLabel(
        String labelName,
        Vector4f color
) {
}
