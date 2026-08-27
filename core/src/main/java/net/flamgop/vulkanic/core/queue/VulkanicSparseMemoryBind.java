package net.flamgop.vulkanic.core.queue;

import net.flamgop.vulkanic.memory.VulkanicDeviceSize;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.vulkan.VkSparseMemoryBind;

public record VulkanicSparseMemoryBind(
        @NotNull VulkanicDeviceSize resourceOffset,
        @NotNull VulkanicDeviceSize size,
        long memory,
        @Nullable VulkanicDeviceSize memoryOffset,
        @NotNull EnumIntBitset<VulkanicSparseMemoryBindFlag> flags
) {
    public void get(@NotNull VkSparseMemoryBind bind) {
        bind
                .resourceOffset(resourceOffset.bytes())
                .size(size.bytes())
                .memory(memory)
                .memoryOffset(memoryOffset != null ? memoryOffset.bytes() : 0)
                .flags(flags.mask());
    }
}
