package net.flamgop.vulkanic.core;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.*;

import java.util.ArrayList;
import java.util.List;

public record VulkanicDeviceFeatures(
        boolean synchronization2,
        boolean bufferDeviceAddress,
        boolean dynamicRendering,
        boolean meshShader,
        boolean taskShader,
        boolean fillModeNonSolid,
        boolean shaderInt64
) {
    public VulkanicDeviceFeatures() {
        this(false, false, false, false, false, false, false);
    }

    @Contract(pure = true, value = "-> new")
    public @NotNull List<String> requiredExtensions() {
        List<String> requiredExtensions = new ArrayList<>();
        if (synchronization2) requiredExtensions.add(KHRSynchronization2.VK_KHR_SYNCHRONIZATION_2_EXTENSION_NAME);
        if (bufferDeviceAddress) requiredExtensions.add(KHRBufferDeviceAddress.VK_KHR_BUFFER_DEVICE_ADDRESS_EXTENSION_NAME);
        if (dynamicRendering) requiredExtensions.add(KHRDynamicRendering.VK_KHR_DYNAMIC_RENDERING_EXTENSION_NAME);
        if (meshShader || taskShader) requiredExtensions.add(EXTMeshShader.VK_EXT_MESH_SHADER_EXTENSION_NAME);
        return requiredExtensions;
    }

    public long pNext(MemoryStack stack) {
        VkPhysicalDeviceVulkan12Features vk12Features = VkPhysicalDeviceVulkan12Features.calloc(stack)
                .sType$Default()
                .bufferDeviceAddress(bufferDeviceAddress);

        VkPhysicalDeviceSynchronization2Features sync2Features = VkPhysicalDeviceSynchronization2Features.calloc(stack)
                .sType$Default()
                .synchronization2(synchronization2)
                .pNext(vk12Features.address());

        VkPhysicalDeviceDynamicRenderingFeatures dynamicRenderingFeatures = VkPhysicalDeviceDynamicRenderingFeatures.calloc(stack)
                .sType$Default()
                .dynamicRendering(dynamicRendering)
                .pNext(sync2Features.address());

        VkPhysicalDeviceMeshShaderFeaturesEXT meshShadingFeatures = VkPhysicalDeviceMeshShaderFeaturesEXT.calloc(stack)
                .sType$Default()
                .meshShader(meshShader)
                .taskShader(taskShader)
                .pNext(dynamicRenderingFeatures.address());

        VkPhysicalDeviceFeatures2 physicalDeviceFeatures2 = VkPhysicalDeviceFeatures2.calloc(stack)
                .sType$Default()
                .features(VkPhysicalDeviceFeatures.calloc(stack)
                        .fillModeNonSolid(fillModeNonSolid)
                        .shaderInt64(shaderInt64)
                )
                .pNext(meshShadingFeatures.address());
        return physicalDeviceFeatures2.address();
    }
}
