package net.flamgop.vulkanic.core.queue;

import net.flamgop.vulkanic.memory.VulkanicDeviceSize;
import net.flamgop.vulkanic.memory.image.VulkanicImageSubresource;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3ic;
import org.lwjgl.vulkan.VkSparseImageMemoryBind;

public record VulkanicSparseImageMemoryBind(
        @NotNull VulkanicImageSubresource subresource,
        @NotNull Vector3ic offset,
        @NotNull Vector3ic extent,
        long memory,
        @Nullable VulkanicDeviceSize memoryOffset,
        @NotNull EnumIntBitset<VulkanicSparseMemoryBindFlag> flags
) {
    public void get(@NotNull VkSparseImageMemoryBind bind) {
        bind
                .subresource(sub -> sub.aspectMask(subresource.aspectMask().mask()).mipLevel(subresource.mipLevel()).arrayLayer(subresource.arrayLayer()))
                .offset(off -> off.set(offset.x(), offset.y(), offset.z()))
                .extent(ext -> ext.set(extent.x(), extent.y(), extent.z()))
                .memory(memory)
                .memoryOffset(memoryOffset != null ? memoryOffset.bytes() : 0)
                .flags(flags.mask());
    }
}
