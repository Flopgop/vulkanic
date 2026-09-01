package net.flamgop.vulkanic.surface;

import net.flamgop.vulkanic.core.VulkanicObject;
import net.flamgop.vulkanic.core.VulkanicObjectType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

// this is platform-dependent. Right now, the helpers implement versions of these you can use. I'm trying to think of a better way to make these.
public interface VulkanicSurface extends AutoCloseable, VulkanicObject.Opaque {
    @ApiStatus.Internal
    @Contract(pure = true)
    @Override
    long handle();

    @Override
    void close();

    @Override
    default @NotNull VulkanicObjectType objectType() {
        return VulkanicObjectType.SURFACE_KHR;
    }
}
