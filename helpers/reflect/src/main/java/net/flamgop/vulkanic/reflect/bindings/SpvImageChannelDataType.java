package net.flamgop.vulkanic.reflect.bindings;

public enum SpvImageChannelDataType {
    SpvImageChannelDataTypeSnormInt8(0),
    SpvImageChannelDataTypeSnormInt16(1),
    SpvImageChannelDataTypeUnormInt8(2),
    SpvImageChannelDataTypeUnormInt16(3),
    SpvImageChannelDataTypeUnormShort565(4),
    SpvImageChannelDataTypeUnormShort555(5),
    SpvImageChannelDataTypeUnormInt101010(6),
    SpvImageChannelDataTypeSignedInt8(7),
    SpvImageChannelDataTypeSignedInt16(8),
    SpvImageChannelDataTypeSignedInt32(9),
    SpvImageChannelDataTypeUnsignedInt8(10),
    SpvImageChannelDataTypeUnsignedInt16(11),
    SpvImageChannelDataTypeUnsignedInt32(12),
    SpvImageChannelDataTypeHalfFloat(13),
    SpvImageChannelDataTypeFloat(14),
    SpvImageChannelDataTypeUnormInt24(15),
    SpvImageChannelDataTypeUnormInt101010_2(16),
    SpvImageChannelDataTypeUnormInt10X6EXT(17),
    SpvImageChannelDataTypeUnsignedIntRaw10EXT(19),
    SpvImageChannelDataTypeUnsignedIntRaw12EXT(20),
    SpvImageChannelDataTypeUnormInt2_101010EXT(21),
    SpvImageChannelDataTypeUnsignedInt10X6EXT(22),
    SpvImageChannelDataTypeUnsignedInt12X4EXT(23),
    SpvImageChannelDataTypeUnsignedInt14X2EXT(24),
    SpvImageChannelDataTypeUnormInt12X4EXT(25),
    SpvImageChannelDataTypeUnormInt14X2EXT(26),
    SpvImageChannelDataTypeMax(2147483647);

    private final int value;

    SpvImageChannelDataType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpvImageChannelDataType fromValue(int value) {
        for (var v : values()) {
            if (v.value == value) return v;
        }
        throw new IllegalArgumentException("Unknown SpvImageChannelDataType value: " + value);
    }
}
