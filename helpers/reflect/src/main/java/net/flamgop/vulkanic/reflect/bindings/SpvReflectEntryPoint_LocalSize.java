package net.flamgop.vulkanic.reflect.bindings;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.VarHandle;

import static java.lang.foreign.MemoryLayout.PathElement;

public final class SpvReflectEntryPoint_LocalSize {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("x"),
            ValueLayout.JAVA_INT.withName("y"),
            ValueLayout.JAVA_INT.withName("z")
    );

    private static final VarHandle VH_X = LAYOUT.varHandle(PathElement.groupElement("x"));
    private static final VarHandle VH_Y = LAYOUT.varHandle(PathElement.groupElement("y"));
    private static final VarHandle VH_Z = LAYOUT.varHandle(PathElement.groupElement("z"));

    private final MemorySegment segment;

    
    public SpvReflectEntryPoint_LocalSize(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectEntryPoint_LocalSize(MemorySegment segment) {
        this.segment = segment.reinterpret(LAYOUT.byteSize());
    }

    
    public MemorySegment segment() {
        return segment;
    }

    public int x() {
        return (int) VH_X.get(segment, 0L);
    }

    public int y() {
        return (int) VH_Y.get(segment, 0L);
    }

    public int z() {
        return (int) VH_Z.get(segment, 0L);
    }
}
