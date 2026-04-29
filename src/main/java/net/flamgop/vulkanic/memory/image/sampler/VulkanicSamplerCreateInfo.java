package net.flamgop.vulkanic.memory.image.sampler;

import net.flamgop.vulkanic.memory.image.VulkanicFilter;
import net.flamgop.vulkanic.pipeline.graphics.VulkanicCompareOp;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.vulkan.VkSamplerCreateInfo;

public record VulkanicSamplerCreateInfo(
        @NotNull EnumIntBitset<VulkanicSamplerCreateFlag> flags,
        @NotNull VulkanicFilter magFilter, @NotNull VulkanicFilter minFilter,
        @NotNull VulkanicSamplerMipmapMode mipmapMode,
        @NotNull VulkanicSamplerAddressMode addressModeU, @NotNull VulkanicSamplerAddressMode addressModeV, @NotNull VulkanicSamplerAddressMode addressModeW,
        float mipLodBias,
        boolean anisotropyEnable, float maxAnisotropy,
        boolean compareEnable, @Nullable VulkanicCompareOp compareOp,
        float minLod, float maxLod,
        @Nullable VulkanicBorderColor borderColor,
        boolean unnormalizedCoordinates
) {

    @Contract(mutates = "param1", value = "_ -> param1")
    public @NotNull VkSamplerCreateInfo populate(@NotNull VkSamplerCreateInfo info) {
        return info
                .sType$Default()
                .flags(this.flags().mask())
                .magFilter(this.magFilter().qualifier()).minFilter(this.minFilter().qualifier())
                .mipmapMode(this.mipmapMode().qualifier())
                .addressModeU(this.addressModeU().qualifier()).addressModeV(this.addressModeV().qualifier()).addressModeW(this.addressModeW().qualifier())
                .mipLodBias(this.mipLodBias())
                .anisotropyEnable(this.anisotropyEnable()).maxAnisotropy(this.maxAnisotropy())
                .compareEnable(this.compareEnable()).compareOp(this.compareOp() != null ? this.compareOp().qualifier() : 0)
                .minLod(this.minLod()).maxLod(this.maxLod())
                .borderColor(this.borderColor() != null ? this.borderColor().qualifier() : 0)
                .unnormalizedCoordinates(this.unnormalizedCoordinates());
    }
}
