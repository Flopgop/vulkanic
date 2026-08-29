package net.flamgop.vulkanic.reflect.bindings;

public enum SpvImageChannelOrder {
    SpvImageChannelOrderR(0),
    SpvImageChannelOrderA(1),
    SpvImageChannelOrderRG(2),
    SpvImageChannelOrderRA(3),
    SpvImageChannelOrderRGB(4),
    SpvImageChannelOrderRGBA(5),
    SpvImageChannelOrderBGRA(6),
    SpvImageChannelOrderARGB(7),
    SpvImageChannelOrderIntensity(8),
    SpvImageChannelOrderLuminance(9),
    SpvImageChannelOrderRx(10),
    SpvImageChannelOrderRGx(11),
    SpvImageChannelOrderRGBx(12),
    SpvImageChannelOrderDepth(13),
    SpvImageChannelOrderDepthStencil(14),
    SpvImageChannelOrdersRGB(15),
    SpvImageChannelOrdersRGBx(16),
    SpvImageChannelOrdersRGBA(17),
    SpvImageChannelOrdersBGRA(18),
    SpvImageChannelOrderABGR(19),
    SpvImageChannelOrderMax(2147483647);

    private final int value;

    SpvImageChannelOrder(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvImageChannelOrder fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvImageChannelOrder value: " + value);
    }
}
