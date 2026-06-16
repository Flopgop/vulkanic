package net.flamgop.vulkanic.glfw;

import net.flamgop.vulkanic.core.VulkanicInstance;
import net.flamgop.vulkanic.surface.VulkanicSurface;
import org.lwjgl.glfw.GLFWVulkan;
import org.lwjgl.system.MemoryStack;

import java.nio.LongBuffer;

public final class GLFWSurface implements VulkanicSurface {

    private static long createSurfaceForWindow(VulkanicInstance instance, long window) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pSurface = stack.callocLong(1);
            GLFWVulkan.glfwCreateWindowSurface(instance.handle(), window, null, pSurface);
            return pSurface.get(0);
        }
    }

    private final VulkanicInstance instance;
    private final long handle;

    public GLFWSurface(VulkanicInstance instance, long window) {
        this.instance = instance;
        this.handle = createSurfaceForWindow(instance, window);
    }

    @Override
    public long handle() {
        return handle;
    }

    @Override
    public void close() {
        instance.destroySurface(this);
    }
}
