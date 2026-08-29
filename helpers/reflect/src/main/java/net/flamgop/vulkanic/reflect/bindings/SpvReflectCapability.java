package net.flamgop.vulkanic.reflect.bindings;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.VarHandle;

import static java.lang.foreign.MemoryLayout.PathElement;

public final class SpvReflectCapability {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("value"),
            ValueLayout.JAVA_INT.withName("word_offset")
    );

    private static final VarHandle VH_VALUE = LAYOUT.varHandle(PathElement.groupElement("value"));
    private static final VarHandle VH_WORD_OFFSET = LAYOUT.varHandle(PathElement.groupElement("word_offset"));

    private final MemorySegment segment;

    
    public SpvReflectCapability(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectCapability(MemorySegment segment) {
        this.segment = segment.reinterpret(LAYOUT.byteSize());
    }

    
    public MemorySegment segment() {
        return segment;
    }

    public SpvCapability value() {
        return SpvCapability.fromValue((int) VH_VALUE.get(segment, 0L));
    }

    public int wordOffset() {
        return (int) VH_WORD_OFFSET.get(segment, 0L);
    }
}
