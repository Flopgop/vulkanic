package net.flamgop.vulkanic.core;

import org.lwjgl.vulkan.*;

public record ApiVersion(int variant, int major, int minor, int patch) {

    public static final ApiVersion VULKAN_1_0 = new ApiVersion(VK10.VK_API_VERSION_1_0);
    public static final ApiVersion VULKAN_1_1 = new ApiVersion(VK11.VK_API_VERSION_1_1);
    public static final ApiVersion VULKAN_1_2 = new ApiVersion(VK12.VK_API_VERSION_1_2);
    public static final ApiVersion VULKAN_1_3 = new ApiVersion(VK13.VK_API_VERSION_1_3);
    public static final ApiVersion VULKAN_1_4 = new ApiVersion(VK14.VK_API_VERSION_1_4);

    public ApiVersion(int packedVersion) {
        this(
                VK10.VK_API_VERSION_VARIANT(packedVersion),
                VK10.VK_API_VERSION_MAJOR(packedVersion),
                VK10.VK_API_VERSION_MINOR(packedVersion),
                VK10.VK_API_VERSION_PATCH(packedVersion)
        );
    }

    public int version() {
        return VK10.VK_MAKE_API_VERSION(variant, major, minor, patch);
    }
}
