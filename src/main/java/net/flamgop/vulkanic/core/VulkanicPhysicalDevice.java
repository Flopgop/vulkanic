package net.flamgop.vulkanic.core;

import net.flamgop.vulkanic.memory.*;
import net.flamgop.vulkanic.memory.image.VulkanicImageCreateFlag;
import net.flamgop.vulkanic.memory.image.VulkanicImageTiling;
import net.flamgop.vulkanic.memory.image.VulkanicImageType;
import net.flamgop.vulkanic.memory.image.VulkanicImageUsageFlag;
import net.flamgop.vulkanic.surface.VulkanicSurface;
import net.flamgop.vulkanic.swapchain.VulkanicSurfaceTransformFlag;
import net.flamgop.vulkanic.util.EnumIntBitset;
import net.flamgop.vulkanic.util.VkUtil;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.vulkan.VK11.*;

public class VulkanicPhysicalDevice {

    private final VkPhysicalDevice handle;

    @ApiStatus.Internal
    public VulkanicPhysicalDevice(VkInstance instance, long handle) {
        this.handle = new VkPhysicalDevice(handle, instance);
    }

    public boolean supportsFeatures(@NotNull VulkanicDeviceFeatures features) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkPhysicalDeviceVulkan12Features vk12 = VkPhysicalDeviceVulkan12Features.calloc(stack).sType$Default();
            VkPhysicalDeviceSynchronization2Features sync2 = VkPhysicalDeviceSynchronization2Features.calloc(stack).sType$Default();
            VkPhysicalDeviceDynamicRenderingFeatures dynamic = VkPhysicalDeviceDynamicRenderingFeatures.calloc(stack).sType$Default();
            VkPhysicalDeviceMeshShaderFeaturesEXT mesh = VkPhysicalDeviceMeshShaderFeaturesEXT.calloc(stack).sType$Default();
            VkPhysicalDeviceExtendedDynamicState3FeaturesEXT extended3 = VkPhysicalDeviceExtendedDynamicState3FeaturesEXT.calloc(stack).sType$Default();

            VkPhysicalDeviceFeatures2 features2 = VkPhysicalDeviceFeatures2.calloc(stack)
                    .sType$Default()
                    .pNext(vk12.address());
            vk12.pNext(sync2.address());
            sync2.pNext(dynamic.address());
            dynamic.pNext(mesh.address());
            mesh.pNext(extended3.address());

            vkGetPhysicalDeviceFeatures2(this.handle, features2);

            if (features.bufferDeviceAddress() && !vk12.bufferDeviceAddress()) return false;
            if (features.synchronization2() && !sync2.synchronization2()) return false;
            if (features.dynamicRendering() && !dynamic.dynamicRendering()) return false;
            if (features.meshShader() && !mesh.meshShader()) return false;
            if (features.taskShader() && !mesh.taskShader()) return false;
            if (features.fillModeNonSolid() && !features2.features().fillModeNonSolid()) return false;
            if (features.shaderInt64() && !features2.features().shaderInt64()) return false;

            return true;
        }
    }

    public @NotNull List<VulkanicExtensionProperties> supportedExtensions() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pCount = stack.callocInt(1);
            vkEnumerateDeviceExtensionProperties(this.handle, (ByteBuffer) null, pCount, null);
            VkExtensionProperties.Buffer pExtensionProperties = VkExtensionProperties.calloc(pCount.get(0)); // note: on certain very capable graphics cards this will overflow the stack, so this is heap allocated.
            vkEnumerateDeviceExtensionProperties(this.handle, (ByteBuffer) null, pCount, pExtensionProperties);

            List<VulkanicExtensionProperties> extensionProperties = new ArrayList<>();
            for (int i = 0; i < pCount.get(0); i++) {
                VkExtensionProperties properties = pExtensionProperties.get(i);
                extensionProperties.add(new VulkanicExtensionProperties(
                        properties.extensionNameString(),
                        properties.specVersion()
                ));
            }

            return extensionProperties;
        }
    }


    public @NotNull VulkanicSurfaceCapabilities surfaceCapabilities(@NotNull VulkanicSurface surface) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkSurfaceCapabilitiesKHR dst = VkSurfaceCapabilitiesKHR.calloc(stack);
            KHRSurface.vkGetPhysicalDeviceSurfaceCapabilitiesKHR(this.handle, surface.handle(), dst);
            return new VulkanicSurfaceCapabilities(
                    dst.minImageCount(), dst.maxImageCount(),
                    VkUtil.toVector2i(dst.currentExtent()), VkUtil.toVector2i(dst.minImageExtent()), VkUtil.toVector2i(dst.maxImageExtent()), dst.maxImageArrayLayers(),
                    new EnumIntBitset<>(dst.supportedTransforms()), VulkanicSurfaceTransformFlag.valueOf(dst.currentTransform()),
                    new EnumIntBitset<>(dst.supportedCompositeAlpha()), new EnumIntBitset<>(dst.supportedUsageFlags())
            );
        }
    }

    @Contract(mutates = "param1", value = "_, _ -> param1")
    public @NotNull VkSurfaceCapabilitiesKHR surfaceCapabilities2(@NotNull VkSurfaceCapabilitiesKHR dst, @NotNull VulkanicSurface surface) {
        KHRSurface.vkGetPhysicalDeviceSurfaceCapabilitiesKHR(this.handle, surface.handle(), dst);
        return dst;
    }

    @Contract(mutates = "param1", value = "_ -> param1")
    public @NotNull VkPhysicalDeviceFeatures2 features2(@NotNull VkPhysicalDeviceFeatures2 dst) {
        vkGetPhysicalDeviceFeatures2(this.handle, dst);
        return dst;
    }

    @Contract(mutates = "param1", value = "_, _ -> param1")
    public @NotNull VkFormatProperties2 formatProperties2(@NotNull VkFormatProperties2 dst, @NotNull VulkanicFormat format) {
        vkGetPhysicalDeviceFormatProperties2(this.handle, format.qualifier(), dst);
        return dst;
    }

    @Contract(mutates = "param1", value = "_, _, _, _, _, _ -> param1")
    public @NotNull VkImageFormatProperties2 imageFormatProperties2(@NotNull VkImageFormatProperties2 dst, @NotNull VulkanicFormat format, @NotNull VulkanicImageType type, @NotNull VulkanicImageTiling tiling, @NotNull EnumIntBitset<VulkanicImageUsageFlag> usage, @NotNull EnumIntBitset<VulkanicImageCreateFlag> flags) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            VkPhysicalDeviceImageFormatInfo2 info = VkPhysicalDeviceImageFormatInfo2.calloc(stack)
                    .format(format.qualifier())
                    .type(type.qualifier())
                    .tiling(tiling.qualifier())
                    .usage(usage.mask())
                    .flags(flags.mask());
            vkGetPhysicalDeviceImageFormatProperties2(this.handle, info, dst);
            return dst;
        }
    }

    @Contract(mutates = "param1", value = "_ -> param1")
    public @NotNull VkPhysicalDeviceProperties2 properties2(@NotNull VkPhysicalDeviceProperties2 dst) {
        vkGetPhysicalDeviceProperties2(this.handle, dst);
        return dst;
    }

    @Contract(mutates = "param1", value = "_ -> param1")
    public @NotNull VkPhysicalDeviceMemoryProperties2 memoryProperties2(@NotNull VkPhysicalDeviceMemoryProperties2 dst) {
        vkGetPhysicalDeviceMemoryProperties2(this.handle, dst);
        return dst;
    }

    @Contract(value = "-> new")
    public @NotNull List<VulkanicQueueFamilyProperties> queueFamilyProperties() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pCount = stack.ints(0);
            vkGetPhysicalDeviceQueueFamilyProperties(this.handle, pCount, null);
            VkQueueFamilyProperties.Buffer pProperties = VkQueueFamilyProperties.calloc(pCount.get(0), stack);
            vkGetPhysicalDeviceQueueFamilyProperties(this.handle, pCount, pProperties);

            List<VulkanicQueueFamilyProperties> properties = new ArrayList<>();
            for (int i = 0; i < pProperties.capacity(); i++) {
                properties.add(new VulkanicQueueFamilyProperties(
                        new EnumIntBitset<>(pProperties.get(i).queueFlags()), pProperties.get(i).queueCount(), pProperties.get(i).timestampValidBits(), VkUtil.toVector3i(pProperties.get(i).minImageTransferGranularity())
                ));
            }
            return properties;
        }
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public @NotNull VkPhysicalDevice handle() {
        return handle;
    }
}
