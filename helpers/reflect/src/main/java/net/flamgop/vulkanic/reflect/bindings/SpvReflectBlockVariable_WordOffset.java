package net.flamgop.vulkanic.reflect.bindings;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.VarHandle;

import static java.lang.foreign.MemoryLayout.PathElement;

public final class SpvReflectBlockVariable_WordOffset {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("offset")
    );

    private static final VarHandle VH_OFFSET = LAYOUT.varHandle(PathElement.groupElement("offset"));

    private final MemorySegment segment;

    
    public SpvReflectBlockVariable_WordOffset(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectBlockVariable_WordOffset(MemorySegment segment) {
        this.segment = segment.reinterpret(LAYOUT.byteSize());
    }

    
    public MemorySegment segment() {
        return segment;
    }

    public int offset() {
        return (int) VH_OFFSET.get(segment, 0L);
    }
}
