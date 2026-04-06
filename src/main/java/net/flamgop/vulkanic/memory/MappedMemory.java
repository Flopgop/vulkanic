package net.flamgop.vulkanic.memory;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryUtil;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.ByteBuffer;

public class MappedMemory implements AutoCloseable {

    private final VulkanicAllocator allocator;
    private final long allocation;
    private final MemorySegment segment;

    /// @see VulkanicBuffer#map
    /// @see net.flamgop.vulkanic.memory.image.VulkanicImage#map
    protected MappedMemory(VulkanicAllocator allocator, long allocation, long address, long size) {
        this.allocator = allocator;
        this.allocation = allocation;
        this.segment = MemorySegment.ofAddress(address).reinterpret(size);
    }

    @Contract(pure = true)
    public @NotNull MemorySegment segment() {
        return segment;
    }

    @Contract(mutates = "this")
    public void putByte(long offset, byte value) {
        segment.set(ValueLayout.JAVA_BYTE, offset, value);
    }

    @Contract(mutates = "this")
    public void putBoolean(long offset, boolean value) {
        segment.set(ValueLayout.JAVA_BYTE, offset, (byte) (value ? 1 : 0));
    }

    @Contract(mutates = "this")
    public void putShort(long offset, short value) {
        segment.set(ValueLayout.JAVA_SHORT, offset, value);
    }

    @Contract(mutates = "this")
    public void putChar(long offset, char value) {
        segment.set(ValueLayout.JAVA_CHAR, offset, value);
    }

    @Contract(mutates = "this")
    public void putInt(long offset, int value) {
        segment.set(ValueLayout.JAVA_INT, offset, value);
    }

    @Contract(mutates = "this")
    public void putLong(long offset, long value) {
        segment.set(ValueLayout.JAVA_LONG, offset, value);
    }

    @Contract(mutates = "this")
    public void putFloat(long offset, float value) {
        segment.set(ValueLayout.JAVA_FLOAT, offset, value);
    }

    @Contract(mutates = "this")
    public void putDouble(long offset, double value) {
        segment.set(ValueLayout.JAVA_DOUBLE, offset, value);
    }

    @Contract(mutates = "this")
    public void putBytes(long offset, byte @NotNull [] values) {
        MemorySegment.copy(values, 0, segment, ValueLayout.JAVA_BYTE, offset, values.length);
    }

    @Contract(mutates = "this")
    public void putBytes(long offset, @NotNull ByteBuffer values) {
        MemorySegment src = MemorySegment.ofAddress(MemoryUtil.memAddress(values)).reinterpret(values.capacity());
        MemorySegment.copy(src, ValueLayout.JAVA_BYTE, values.position(), segment, ValueLayout.JAVA_BYTE, offset, values.remaining());
    }

    @Contract(mutates = "this")
    public void putInts(long offset, int @NotNull [] values) {
        MemorySegment.copy(values, 0, segment, ValueLayout.JAVA_INT, offset, values.length);
    }

    @Contract(mutates = "this")
    public void putLongs(long offset, long @NotNull [] values) {
        MemorySegment.copy(values, 0, segment, ValueLayout.JAVA_LONG, offset, values.length);
    }

    @Contract(mutates = "this")
    public void putFloats(long offset, float @NotNull [] values) {
        MemorySegment.copy(values, 0, segment, ValueLayout.JAVA_FLOAT, offset, values.length);
    }

    @Override
    public void close() {
        allocator.unmapMemory(this.allocation);
    }
}
