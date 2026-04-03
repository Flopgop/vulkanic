package net.flamgop.vulkanic.core;

import net.flamgop.vulkanic.surface.VulkanicSurface;
import net.flamgop.vulkanic.util.VkUtil;
import org.jetbrains.annotations.*;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWVulkan;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkInstanceCreateInfo;

import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VulkanicInstance implements AutoCloseable {
    private final @NotNull VulkanicApplicationInfo applicationInfo;
    private final @Unmodifiable @NotNull List<String> enabledLayers;
    private final @Unmodifiable @NotNull List<String> enabledExtensions;
    private final VkInstance handle;

    public VulkanicInstance(@NotNull VulkanicApplicationInfo applicationInfo, @NotNull Collection<String> layers, @NotNull Collection<String> extensions) {
        this.applicationInfo = applicationInfo;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer ret = stack.callocPointer(1);
            PointerBuffer layersBuf = stack.callocPointer(layers.size());
            PointerBuffer extensionsBuf = stack.callocPointer(extensions.size());
            VkUtil.copyToBuffer(layersBuf, stack, layers);
            VkUtil.copyToBuffer(extensionsBuf, stack, extensions);
            layersBuf.flip();
            extensionsBuf.flip();

            VkInstanceCreateInfo createInfo = VkInstanceCreateInfo.calloc(stack)
                    .sType$Default()
                    .pApplicationInfo(applicationInfo.calloc(stack))
                    .ppEnabledLayerNames(layersBuf)
                    .ppEnabledExtensionNames(extensionsBuf);

            this.enabledLayers = List.copyOf(layers);
            this.enabledExtensions = List.copyOf(extensions);

            VkUtil.check(VK10.vkCreateInstance(createInfo, null, ret));
            this.handle = new VkInstance(ret.get(0), createInfo);
        }
    }

    public @NotNull List<VulkanicPhysicalDevice> enumeratePhysicalDevices() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pCount = stack.callocInt(1);
            VK10.vkEnumeratePhysicalDevices(this.handle, pCount, null);
            PointerBuffer pDevices = stack.callocPointer(pCount.get(0));
            VK10.vkEnumeratePhysicalDevices(this.handle, pCount, pDevices);

            List<VulkanicPhysicalDevice> physicalDevices = new ArrayList<>();
            for (int i = 0; i < pDevices.capacity(); i++) {
                physicalDevices.add(new VulkanicPhysicalDevice(this.handle, pDevices.get(i)));
            }
            return physicalDevices;
        }
    }

    public @NotNull VulkanicSurface createGlfwWindowSurface(long windowHandle) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pSurface = stack.callocLong(1);
            VkUtil.check(GLFWVulkan.glfwCreateWindowSurface(this.handle, windowHandle, null, pSurface));
            return new VulkanicSurface(this, pSurface.get(0));
        }
    }

    @Contract(pure = true)
    public @NotNull VulkanicApplicationInfo applicationInfo() {
        return applicationInfo;
    }

    @Contract(pure = true)
    public @UnmodifiableView @NotNull List<String> enabledLayers() {
        return enabledLayers;
    }

    @Contract(pure = true)
    public @UnmodifiableView @NotNull List<String> enabledExtensions() {
        return enabledExtensions;
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public @NotNull VkInstance handle() {
        return handle;
    }

    @Override
    public void close() {
        VK10.vkDestroyInstance(handle, null);
    }
}
