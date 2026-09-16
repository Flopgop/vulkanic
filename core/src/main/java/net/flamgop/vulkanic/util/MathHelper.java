package net.flamgop.vulkanic.util;

public final class MathHelper {
    public static boolean fitsInInt(long value) {
        return value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE;
    }
}
