package net.flamgop.vulkanic.memory.image;

import net.flamgop.vulkanic.memory.MappedMemory;
import net.flamgop.vulkanic.memory.VulkanicAllocator;
import net.flamgop.vulkanic.memory.VulkanicFormat;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3ic;
import org.lwjgl.util.vma.VmaAllocationInfo;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VK11;

public class VulkanicImage implements AutoCloseable {

    private static boolean isDepthFormat(VulkanicFormat format) {
        return switch (format) {
            case D16_UNORM, X8_D24_UNORM_PACK32, D32_SFLOAT,
                 D16_UNORM_S8_UINT, D24_UNORM_S8_UINT, D32_SFLOAT_S8_UINT -> true;
            default -> false;
        };
    }

    private static boolean isStencilFormat(VulkanicFormat format) {
        return switch (format) {
            case S8_UINT, D16_UNORM_S8_UINT,
                 D24_UNORM_S8_UINT, D32_SFLOAT_S8_UINT -> true;
            default -> false;
        };
    }

    private static int computeAspectMask(VulkanicFormat format) {
        switch (format) {
            case G8_B8_R8_3PLANE_420_UNORM:
            case G8_B8_R8_3PLANE_422_UNORM:
            case G8_B8_R8_3PLANE_444_UNORM:
                return VK11.VK_IMAGE_ASPECT_PLANE_0_BIT | VK11.VK_IMAGE_ASPECT_PLANE_1_BIT | VK11.VK_IMAGE_ASPECT_PLANE_2_BIT;

            case G8_B8R8_2PLANE_420_UNORM:
            case G8_B8R8_2PLANE_422_UNORM:
                return VK11.VK_IMAGE_ASPECT_PLANE_0_BIT | VK11.VK_IMAGE_ASPECT_PLANE_1_BIT;
        }

        boolean hasDepth = isDepthFormat(format);
        boolean hasStencil = isStencilFormat(format);

        if (hasDepth || hasStencil) {
            int mask = 0;
            if (hasDepth) mask |= VK10.VK_IMAGE_ASPECT_DEPTH_BIT;
            if (hasStencil) mask |= VK10.VK_IMAGE_ASPECT_STENCIL_BIT;
            return mask;
        }

        return VK10.VK_IMAGE_ASPECT_COLOR_BIT;
    }

    private final VulkanicAllocator allocator;
    private final long handle;
    private final long allocation;

    private final EnumIntBitset<VulkanicImageAspectFlag> aspectMask;
    private final int memoryType;
    private final long deviceMemory;
    private final long offset;
    private final long size;

    private final boolean special;

    private final VulkanicFormat format;
    private final Vector3ic extent;
    private final int mipLevels;
    private final EnumIntBitset<VulkanicImageUsageFlag> usage;

    /// @see VulkanicAllocator#createImage
    @ApiStatus.Internal
    public VulkanicImage(VulkanicAllocator allocator, long handle, long allocation, VmaAllocationInfo allocationInfo, VulkanicImageCreateInfo createInfo) {
        this.allocator = allocator;
        this.handle = handle;
        this.allocation = allocation;

        this.aspectMask = new EnumIntBitset<>(computeAspectMask(createInfo.format()));
        this.memoryType = allocationInfo.memoryType();
        this.deviceMemory = allocationInfo.deviceMemory();
        this.offset = allocationInfo.offset();
        this.size = allocationInfo.size();
        this.format = createInfo.format();
        this.extent = createInfo.extent();
        this.mipLevels = createInfo.mipLevels();
        this.usage = createInfo.usage();

        this.special = false;
    }

    /// @see VulkanicAllocator#createImage
    @ApiStatus.Internal
    public VulkanicImage(long handle, @NotNull VulkanicFormat format, @NotNull Vector3ic extent, int mipLevels, @NotNull EnumIntBitset<VulkanicImageUsageFlag> usage) {
        this.allocator = null;
        this.handle = handle;
        this.aspectMask = new EnumIntBitset<>(computeAspectMask(format));
        this.allocation = 0;
        this.memoryType = 0;
        this.deviceMemory = 0;
        this.offset = 0;
        this.size = 0;
        this.special = true;

        this.format = format;
        this.extent = extent;
        this.mipLevels = mipLevels;
        this.usage = usage;
    }

    public @NotNull MappedMemory map() {
        if (special) throw new UnsupportedOperationException("Cannot map special images (no associated memory)");
        return this.allocator.mapMemory(this.allocation);
    }

    public @NotNull VulkanicFormat format() {
        return format;
    }

    public @NotNull Vector3ic extent() {
        return extent;
    }

    public int mipLevels() {
        return mipLevels;
    }

    public EnumIntBitset<VulkanicImageUsageFlag> usage() {
        return usage;
    }

    public EnumIntBitset<VulkanicImageAspectFlag> aspectMask() {
        return aspectMask;
    }

    public int memoryType() {
        return this.memoryType;
    }

    public long deviceMemory() {
        return this.deviceMemory;
    }

    public long offset() {
        return this.offset;
    }

    public long size() {
        return this.size;
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public long allocation() {
        return allocation;
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public long handle() {
        return handle;
    }

    @Override
    public void close() {
        if (special) return; // not valid
        this.allocator.destroyImage(this);
    }
}
