package net.flamgop.vulkanic.exception;

public class VulkanException extends Exception {
    private final VulkanicResult result;

    public VulkanException(VulkanicResult result) {
        super(String.format("Vulkan Error: %s", result));
        this.result = result;
    }

    public VulkanicResult result() {
        return result;
    }
}
