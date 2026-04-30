package net.flamgop.vulkanic.memory;

import org.lwjgl.util.vma.Vma;

public enum VulkanicMemoryUsage {
    UNKNOWN(Vma.VMA_MEMORY_USAGE_UNKNOWN),
    @Deprecated
    GPU_ONLY(Vma.VMA_MEMORY_USAGE_GPU_ONLY),
    @Deprecated
    CPU_ONLY(Vma.VMA_MEMORY_USAGE_CPU_ONLY),
    @Deprecated
    CPU_TO_GPU(Vma.VMA_MEMORY_USAGE_CPU_TO_GPU),
    @Deprecated
    GPU_TO_CPU(Vma.VMA_MEMORY_USAGE_GPU_TO_CPU),
    @Deprecated
    CPU_COPY(Vma.VMA_MEMORY_USAGE_CPU_COPY),
    GPU_LAZILY_ALLOCATED(Vma.VMA_MEMORY_USAGE_GPU_LAZILY_ALLOCATED),
    AUTO(Vma.VMA_MEMORY_USAGE_AUTO),
    AUTO_PREFER_DEVICE(Vma.VMA_MEMORY_USAGE_AUTO_PREFER_DEVICE),
    AUTO_PREFER_HOST(Vma.VMA_MEMORY_USAGE_AUTO_PREFER_HOST),
    ;
    private final int qualifier;
    VulkanicMemoryUsage(int qualifier) {
        this.qualifier = qualifier;
    }
    public int qualifier() {
        return this.qualifier;
    }
}
