package net.flamgop.vulkanic.reflect.bindings;

public enum SpvFPEncoding {
    SpvFPEncodingBFloat16KHR(0),
    SpvFPEncodingFloat8E4M3EXT(4214),
    SpvFPEncodingFloat8E5M2EXT(4215),
    SpvFPEncodingFloat6E2M3EXT(4223),
    SpvFPEncodingFloat6E3M2EXT(4224),
    SpvFPEncodingFloat4E2M1EXT(4225),
    SpvFPEncodingFloat8UnsignedE8M0EXT(4226),
    SpvFPEncodingMXInt8EXT(4227),
    SpvFPEncodingMax(2147483647);

    private final int value;

    SpvFPEncoding(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvFPEncoding fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvFPEncoding value: " + value);
    }
}
