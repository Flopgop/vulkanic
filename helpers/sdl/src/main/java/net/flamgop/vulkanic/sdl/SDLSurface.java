package net.flamgop.vulkanic.sdl;

import net.flamgop.vulkanic.core.VulkanicInstance;
import net.flamgop.vulkanic.surface.VulkanicSurface;
import org.lwjgl.sdl.SDLError;
import org.lwjgl.sdl.SDLVulkan;
import org.lwjgl.system.MemoryStack;

import java.nio.LongBuffer;

public final class SDLSurface implements VulkanicSurface {

    private static long createSurfaceForWindow(VulkanicInstance instance, long window) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            LongBuffer pSurface = stack.callocLong(1);
            SDLVulkan.SDL_Vulkan_CreateSurface(window, instance.handle(), null, pSurface);
            return pSurface.get(0);
        }
    }

    private final VulkanicInstance instance;
    private final long handle;

    public SDLSurface(VulkanicInstance instance, long window) {
        this.instance = instance;
        this.handle = createSurfaceForWindow(instance, window);
        if (this.handle == 0x0) throw new IllegalStateException("Failed to create SDLSurface: " + SDLError.SDL_GetError());
    }

    @Override
    public long handle() {
        return handle;
    }

    @Override
    public void close() {
        SDLVulkan.SDL_Vulkan_DestroySurface(this.instance.handle(), this.handle(), null);
    }
}
