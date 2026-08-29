package net.flamgop.vulkanic.reflect.bindings;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.VarHandle;

import static java.lang.foreign.MemoryLayout.PathElement;

public final class SpvReflectNumericTraits_Vector {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("component_count")
    );

    private static final VarHandle VH_COMPONENT_COUNT = LAYOUT.varHandle(PathElement.groupElement("component_count"));

    private final MemorySegment segment;

    
    public SpvReflectNumericTraits_Vector(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectNumericTraits_Vector(MemorySegment segment) {
        this.segment = segment.reinterpret(LAYOUT.byteSize());
    }

    
    public MemorySegment segment() {
        return segment;
    }

    public int componentCount() {
        return (int) VH_COMPONENT_COUNT.get(segment, 0L);
    }
}
