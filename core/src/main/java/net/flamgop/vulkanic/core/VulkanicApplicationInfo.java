package net.flamgop.vulkanic.core;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkApplicationInfo;

/// @param applicationName The name of this application
/// @param applicationVersion An [ApiVersion] object containing the version of this application
/// @param engineName The name of this engine
/// @param engineVersion An [ApiVersion] object containing the version of this engine
/// @param apiVersion The minimum required Vulkan version this app requires.
/// @see ApiVersion#VULKAN_1_0
/// @see ApiVersion#VULKAN_1_1
/// @see ApiVersion#VULKAN_1_2
/// @see ApiVersion#VULKAN_1_3
/// @see ApiVersion#VULKAN_1_4
public record VulkanicApplicationInfo(String applicationName, ApiVersion applicationVersion, String engineName, ApiVersion engineVersion, ApiVersion apiVersion) {
    @Contract(mutates = "param1", value = "_ -> new")
    public @NotNull VkApplicationInfo calloc(@NotNull MemoryStack stack) {
        return VkApplicationInfo.calloc(stack)
                .pApplicationName(stack.UTF8(applicationName))
                .applicationVersion(applicationVersion.version())
                .pEngineName(stack.UTF8(engineName))
                .engineVersion(engineVersion.version())
                .apiVersion(apiVersion.version());
    }
}
