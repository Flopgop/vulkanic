package net.flamgop.vulkanic.sync;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.KHRVideoQueue;
import org.lwjgl.vulkan.VK10;

public enum VulkanicQueryResultFlag implements Bitmaskable<Integer> {
    RESULT_64(VK10.VK_QUERY_RESULT_64_BIT),
    RESULT_WAIT(VK10.VK_QUERY_RESULT_WAIT_BIT),
    RESULT_WITH_AVAILABILITY(VK10.VK_QUERY_RESULT_WITH_AVAILABILITY_BIT),
    RESULT_WITH_PARTIAL(VK10.VK_QUERY_RESULT_PARTIAL_BIT),

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
