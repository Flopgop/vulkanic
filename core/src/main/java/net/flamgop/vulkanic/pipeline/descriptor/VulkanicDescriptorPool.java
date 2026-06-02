package net.flamgop.vulkanic.pipeline.descriptor;

import net.flamgop.vulkanic.core.VulkanicDevice;
import net.flamgop.vulkanic.exception.VulkanException;
import net.flamgop.vulkanic.util.EnumIntBitset;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VulkanicDescriptorPool implements AutoCloseable {

    private final VulkanicDevice device;
    private final VulkanicDescriptorPoolCreateInfo createInfo;
    private final long handle;

    /// @see VulkanicDevice#createDescriptorPool
    @ApiStatus.Internal
    public VulkanicDescriptorPool(@NotNull VulkanicDevice device, @NotNull VulkanicDescriptorPoolCreateInfo createInfo, long handle) {
        this.device = device;
        this.createInfo = createInfo;
        this.handle = handle;
    }

    @Contract(pure = true)
    public @NotNull VulkanicDescriptorPoolCreateInfo createInfo() {
        return createInfo;
    }

    public @NotNull VulkanicDescriptorSet[] allocate(List<VulkanicDescriptorSetLayout> layouts) throws VulkanException {
        return device.allocateDescriptorSets(this, layouts);
    }

    public void free(@NotNull VulkanicDescriptorSet... descriptorSets) {
        device.freeDescriptorSets(this, descriptorSets);
    }

    public void reset(@NotNull EnumIntBitset<VulkanicDescriptorPoolResetFlag> flags) throws VulkanException {
        device.resetDescriptorPool(this, flags);
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public long handle() {
        return handle;
    }

    @Override
    public void close() {
        device.destroyDescriptorPool(this);
    }
}
