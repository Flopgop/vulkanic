package net.flamgop.vulkanic.surface;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

// this is platform-dependent. Right now, the helpers implement versions of these you can use. I'm trying to think of a better way to make these.
public interface VulkanicSurface extends AutoCloseable {
    @ApiStatus.Internal
    @Contract(pure = true)
    long handle();

    @Override
    void close();
}
