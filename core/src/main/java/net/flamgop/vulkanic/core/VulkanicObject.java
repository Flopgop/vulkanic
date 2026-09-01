package net.flamgop.vulkanic.core;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.Pointer;

public sealed interface VulkanicObject {
    @NotNull VulkanicObjectType objectType();
    non-sealed interface Opaque extends VulkanicObject {
        long handle();
    }
    non-sealed interface Typed<T extends Pointer.Default> extends VulkanicObject {
        T handle();
    }
}
