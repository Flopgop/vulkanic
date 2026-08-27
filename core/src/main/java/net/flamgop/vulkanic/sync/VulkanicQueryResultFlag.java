package net.flamgop.vulkanic.sync;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.KHRVideoQueue;
import org.lwjgl.vulkan.VK10;

public enum VulkanicQueryResultFlag implements Bitmaskable<Integer> {
    /// Specifies the results will be written as an array of 64-bit unsigned integer values.
    RESULT_64(VK10.VK_QUERY_RESULT_64_BIT),
    /// Specifies that Vulkan will wait for each query's status to become available before retrieving its results.
    RESULT_WAIT(VK10.VK_QUERY_RESULT_WAIT_BIT),
    /// Specifies that the availability status accompanies the results.
    RESULT_WITH_AVAILABILITY(VK10.VK_QUERY_RESULT_WITH_AVAILABILITY_BIT),
    /// Specifies that returning partial results is acceptable.
    RESULT_WITH_PARTIAL(VK10.VK_QUERY_RESULT_PARTIAL_BIT),

    /// Specifies that the last value returned in the query is a VkQueryResultStatusKHR value.
    RESULT_WITH_STATUS_KHR(KHRVideoQueue.VK_QUERY_RESULT_WITH_STATUS_BIT_KHR)

    ;
    private final int flag;
    VulkanicQueryResultFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}
