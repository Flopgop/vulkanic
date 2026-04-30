package net.flamgop.vulkanic.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;
import org.joml.Vector3i;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.*;

import java.time.Duration;
import java.util.Collection;

public class VkUtil {

    public static final Duration FOREVER = Duration.ofNanos(Long.MAX_VALUE);

    public static Vector2i toVector2i(VkExtent2D extent2D) {
        return new Vector2i(extent2D.width(), extent2D.height());
    }

    public static Vector3i toVector3i(VkExtent3D extent3D) {
        return new Vector3i(extent3D.width(), extent3D.height(), extent3D.depth());
    }

    public static String errorToString(int ret) {
        return switch (ret) {
            case VK10.VK_SUCCESS -> "VK_SUCCESS";
            case VK10.VK_NOT_READY -> "VK_NOT_READY";
            case VK10.VK_TIMEOUT -> "VK_TIMEOUT";
            case VK10.VK_EVENT_SET -> "VK_EVENT_SET";
            case VK10.VK_EVENT_RESET -> "VK_EVENT_RESET";
            case VK10.VK_INCOMPLETE -> "VK_INCOMPLETE";
            case VK10.VK_ERROR_OUT_OF_HOST_MEMORY -> "VK_ERROR_OUT_OF_HOST_MEMORY";
            case VK10.VK_ERROR_OUT_OF_DEVICE_MEMORY -> "VK_ERROR_OUT_OF_DEVICE_MEMORY";
            case VK10.VK_ERROR_INITIALIZATION_FAILED -> "VK_ERROR_INITIALIZATION_FAILED";
            case VK10.VK_ERROR_DEVICE_LOST -> "VK_ERROR_DEVICE_LOST";
            case VK10.VK_ERROR_MEMORY_MAP_FAILED -> "VK_ERROR_MEMORY_MAP_FAILED";
            case VK10.VK_ERROR_LAYER_NOT_PRESENT -> "VK_ERROR_LAYER_NOT_PRESENT";
            case VK10.VK_ERROR_EXTENSION_NOT_PRESENT -> "VK_ERROR_EXTENSION_NOT_PRESENT";
            case VK10.VK_ERROR_FEATURE_NOT_PRESENT -> "VK_ERROR_FEATURE_NOT_PRESENT";
            case VK10.VK_ERROR_INCOMPATIBLE_DRIVER -> "VK_ERROR_INCOMPATIBLE_DRIVER";
            case VK10.VK_ERROR_TOO_MANY_OBJECTS -> "VK_ERROR_TOO_MANY_OBJECTS";
            case VK10.VK_ERROR_FORMAT_NOT_SUPPORTED -> "VK_ERROR_FORMAT_NOT_SUPPORTED";
            case VK10.VK_ERROR_FRAGMENTED_POOL -> "VK_ERROR_FRAGMENTED_POOL";
            case VK10.VK_ERROR_UNKNOWN -> "VK_ERROR_UNKNOWN";
            case VK10.VK_ERROR_VALIDATION_FAILED -> "VK_ERROR_VALIDATION_FAILED";
            case VK11.VK_ERROR_OUT_OF_POOL_MEMORY -> "VK_ERROR_OUT_OF_POOL_MEMORY";
            case VK11.VK_ERROR_INVALID_EXTERNAL_HANDLE -> "VK_ERROR_INVALID_EXTERNAL_HANDLE";
            case VK12.VK_ERROR_FRAGMENTATION -> "VK_ERROR_FRAGMENTATION";
            case VK12.VK_ERROR_INVALID_OPAQUE_CAPTURE_ADDRESS -> "VK_ERROR_INVALID_OPAQUE_CAPTURE_ADDRESS";
            case VK14.VK_ERROR_NOT_PERMITTED -> "VK_ERROR_NOT_PERMITTED";
            case KHRSurface.VK_ERROR_SURFACE_LOST_KHR -> "VK_ERROR_SURFACE_LOST_KHR";
            case KHRSurface.VK_ERROR_NATIVE_WINDOW_IN_USE_KHR -> "VK_ERROR_NATIVE_WINDOW_IN_USE_KHR";
            case KHRSwapchain.VK_ERROR_OUT_OF_DATE_KHR -> "VK_ERROR_OUT_OF_DATE_KHR";
            case KHRDisplaySwapchain.VK_ERROR_INCOMPATIBLE_DISPLAY_KHR -> "VK_ERROR_INCOMPATIBLE_DISPLAY_KHR";
            case NVGLSLShader.VK_ERROR_INVALID_SHADER_NV -> "VK_ERROR_INVALID_SHADER_NV";
            case KHRVideoQueue.VK_ERROR_IMAGE_USAGE_NOT_SUPPORTED_KHR -> "VK_ERROR_IMAGE_USAGE_NOT_SUPPORTED_KHR";
            case KHRVideoQueue.VK_ERROR_VIDEO_PICTURE_LAYOUT_NOT_SUPPORTED_KHR -> "VK_ERROR_VIDEO_PICTURE_LAYOUT_NOT_SUPPORTED_KHR";
            case KHRVideoQueue.VK_ERROR_VIDEO_PROFILE_OPERATION_NOT_SUPPORTED_KHR -> "VK_ERROR_VIDEO_PROFILE_OPERATION_NOT_SUPPORTED_KHR";
            case KHRVideoQueue.VK_ERROR_VIDEO_PROFILE_CODEC_NOT_SUPPORTED_KHR -> "VK_ERROR_VIDEO_PROFILE_CODEC_NOT_SUPPORTED_KHR";
            case KHRVideoQueue.VK_ERROR_VIDEO_STD_VERSION_NOT_SUPPORTED_KHR -> "VK_ERROR_VIDEO_STD_VERSION_NOT_SUPPORTED_KHR";
            case EXTImageDrmFormatModifier.VK_ERROR_INVALID_DRM_FORMAT_MODIFIER_PLANE_LAYOUT_EXT -> "VK_ERROR_INVALID_DRM_FORMAT_MODIFIER_PLANE_LAYOUT_EXT";
            case EXTPresentTiming.VK_ERROR_PRESENT_TIMING_QUEUE_FULL_EXT -> "VK_ERROR_PRESENT_TIMING_QUEUE_FULL_EXT";
            case EXTFullScreenExclusive.VK_ERROR_FULL_SCREEN_EXCLUSIVE_MODE_LOST_EXT -> "VK_ERROR_FULL_SCREEN_EXCLUSIVE_MODE_LOST_EXT";
            case KHRVideoEncodeQueue.VK_ERROR_INVALID_VIDEO_STD_PARAMETERS_KHR -> "VK_ERROR_INVALID_VIDEO_STD_PARAMETERS_KHR";
            case KHRPipelineBinary.VK_ERROR_NOT_ENOUGH_SPACE_KHR -> "VK_ERROR_NOT_ENOUGH_SPACE_KHR";
            // TODO: read every extension that has ever and will ever exist and make sure this is every error
            default -> "(couldn't map error to string)";
        };
    }

    public static void check(int ret) {
        if (ret < 0) { // note: *technically* there are other results that are not VK_SUCCESS, but are positive values and aren't technically fatal errors, and you probably don't want an assert to fail for a basic suboptimal error or something
            throw new AssertionError(String.format("VkUtil#check failed with %s (error code: %d)", errorToString(ret), ret));
        }
    }

    @Contract(mutates = "param1, param2")
    public static void copyToBuffer(@NotNull PointerBuffer dst, @NotNull MemoryStack stack, @NotNull Collection<String> strings) {
        if (dst.remaining() < strings.size()) throw new IndexOutOfBoundsException("Not enough space in destination PointerBuffer");
        for (String string : strings) {
            dst.put(stack.UTF8(string));
        }
    }
}
