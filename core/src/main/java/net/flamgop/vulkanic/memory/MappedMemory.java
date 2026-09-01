package net.flamgop.vulkanic.memory;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryUtil;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.ByteBuffer;

public interface MappedMemory extends AutoCloseable {
    @NotNull MemorySegment segment();
    @Override void close();

    @Contract(mutates = "this")
    default void putByte(long offset, byte value) {
        segment().set(ValueLayout.JAVA_BYTE, offset, value);
    }

    @Contract(mutates = "this")
    default void putBoolean(long offset, boolean value) {
        segment().set(ValueLayout.JAVA_BYTE, offset, (byte) (value ? 1 : 0));
    }

    @Contract(mutates = "this")
    default void putShort(long offset, short value) {
        segment().set(ValueLayout.JAVA_SHORT, offset, value);
    }

    @Contract(mutates = "this")
    default void putChar(long offset, char value) {
        segment().set(ValueLayout.JAVA_CHAR, offset, value);
    }

    @Contract(mutates = "this")
    default void putInt(long offset, int value) {
        segment().set(ValueLayout.JAVA_INT, offset, value);
    }

    @Contract(mutates = "this")
    default void putLong(long offset, long value) {
        segment().set(ValueLayout.JAVA_LONG, offset, value);
    }

    @Contract(mutates = "this")
    default void putFloat(long offset, float value) {
        segment().set(ValueLayout.JAVA_FLOAT, offset, value);
    }

    @Contract(mutates = "this")
    default void putDouble(long offset, double value) {
        segment().set(ValueLayout.JAVA_DOUBLE, offset, value);
    }

    @Contract(mutates = "this")
    default void putBytes(long offset, byte @NotNull [] values) {
        MemorySegment.copy(values, 0, segment(), ValueLayout.JAVA_BYTE, offset, values.length);
    }

    @Contract(mutates = "this")
    default void putBytes(long offset, @NotNull ByteBuffer values) {
        MemorySegment src = MemorySegment.ofAddress(MemoryUtil.memAddress(values)).reinterpret(values.capacity());
        MemorySegment.copy(src, ValueLayout.JAVA_BYTE, values.position(), segment(), ValueLayout.JAVA_BYTE, offset, values.remaining());
    }

    @Contract(mutates = "this")
    default void putInts(long offset, int @NotNull [] values) {
        MemorySegment.copy(values, 0, segment(), ValueLayout.JAVA_INT, offset, values.length);
    }

    @Contract(mutates = "this")
    default void putLongs(long offset, long @NotNull [] values) {
        MemorySegment.copy(values, 0, segment(), ValueLayout.JAVA_LONG, offset, values.length);
    }

    @Contract(mutates = "this")
    default void putFloats(long offset, float @NotNull [] values) {
        MemorySegment.copy(values, 0, segment(), ValueLayout.JAVA_FLOAT, offset, values.length);
    }

    @Contract(pure = true)
    default byte getByte(long offset) {
        return segment().get(ValueLayout.JAVA_BYTE, offset);
    }

    @Contract(pure = true)
    default boolean getBoolean(long offset) {
        return segment().get(ValueLayout.JAVA_BYTE, offset) != 0;
    }

    @Contract(pure = true)
    default short getShort(long offset) {
        return segment().get(ValueLayout.JAVA_SHORT, offset);
    }

    @Contract(pure = true)
    default char getChar(long offset) {
        return segment().get(ValueLayout.JAVA_CHAR, offset);
    }

    @Contract(pure = true)
    default int getInt(long offset) {
        return segment().get(ValueLayout.JAVA_INT, offset);
    }

    @Contract(pure = true)
    default long getLong(long offset) {
        return segment().get(ValueLayout.JAVA_LONG, offset);
    }

    @Contract(pure = true)
    default float getFloat(long offset) {
        return segment().get(ValueLayout.JAVA_FLOAT, offset);
    }

    @Contract(pure = true)
    default double getDouble(long offset) {
        return segment().get(ValueLayout.JAVA_DOUBLE, offset);
    }

    @Contract(pure = true)
    default void getBytes(long offset, byte @NotNull [] destination) {
        MemorySegment.copy(segment(), ValueLayout.JAVA_BYTE, offset, destination, 0, destination.length);
    }

    @Contract(pure = true)
    default void getInts(long offset, int @NotNull [] destination) {
        MemorySegment.copy(segment(), ValueLayout.JAVA_INT, offset, destination, 0, destination.length);
    }

    @Contract(pure = true)
    default void getLongs(long offset, long @NotNull [] destination) {
        MemorySegment.copy(segment(), ValueLayout.JAVA_LONG, offset, destination, 0, destination.length);
    }

    @Contract(pure = true)
    default void getFloats(long offset, float @NotNull [] destination) {
        MemorySegment.copy(segment(), ValueLayout.JAVA_FLOAT, offset, destination, 0, destination.length);
    }
}
