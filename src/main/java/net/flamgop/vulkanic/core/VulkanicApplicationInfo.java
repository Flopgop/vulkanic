package net.flamgop.vulkanic.core;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkApplicationInfo;

public record VulkanicApplicationInfo(String applicationName, ApiVersion applicationVersion, String engineName, ApiVersion engineVersion, ApiVersion apiVersion) {
    public VkApplicationInfo calloc(MemoryStack stack) {
        return VkApplicationInfo.calloc(stack)
                .pApplicationName(stack.UTF8(applicationName))
                .applicationVersion(applicationVersion.version())
                .pEngineName(stack.UTF8(engineName))
                .engineVersion(engineVersion.version())
                .apiVersion(apiVersion.version());
    }
}
