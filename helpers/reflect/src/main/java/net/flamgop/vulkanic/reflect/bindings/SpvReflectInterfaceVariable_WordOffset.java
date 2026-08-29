package net.flamgop.vulkanic.reflect.bindings;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.VarHandle;

import static java.lang.foreign.MemoryLayout.PathElement;

public final class SpvReflectInterfaceVariable_WordOffset {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("location")
    );

    private static final VarHandle VH_LOCATION = LAYOUT.varHandle(PathElement.groupElement("location"));

    private final MemorySegment segment;

    
    public SpvReflectInterfaceVariable_WordOffset(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectInterfaceVariable_WordOffset(MemorySegment segment) {
        this.segment = segment.reinterpret(LAYOUT.byteSize());
    }

    
    public MemorySegment segment() {
        return segment;
    }

    public int location() {
        return (int) VH_LOCATION.get(segment, 0L);
    }
}
