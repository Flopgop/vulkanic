package net.flamgop.vulkanic.pipeline.descriptor;

import net.flamgop.vulkanic.core.VulkanicDevice;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VulkanicDescriptorPool implements AutoCloseable {

    private final VulkanicDevice device;
    private final long handle;

    @ApiStatus.Internal
    public VulkanicDescriptorPool(@NotNull VulkanicDevice device, long handle) {
        this.device = device;
        this.handle = handle;
    }

    public @NotNull VulkanicDescriptorSet[] allocate(List<VulkanicDescriptorSetLayout> layouts) {
        return device.allocateDescriptorSets(this, layouts);
    }

    public long handle() {
        return handle;
    }

    @Override
    public void close() {
        device.destroyDescriptorPool(this);
    }
}
