package net.flamgop.vulkanic.reflect.bindings;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.VarHandle;

import static java.lang.foreign.MemoryLayout.PathElement;

public final class SpvReflectNumericTraits_Scalar {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("width"),
            ValueLayout.JAVA_INT.withName("signedness")
    );

    private static final VarHandle VH_WIDTH = LAYOUT.varHandle(PathElement.groupElement("width"));
    private static final VarHandle VH_SIGNEDNESS = LAYOUT.varHandle(PathElement.groupElement("signedness"));

    private final MemorySegment segment;

    
    public SpvReflectNumericTraits_Scalar(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectNumericTraits_Scalar(MemorySegment segment) {
        this.segment = segment.reinterpret(LAYOUT.byteSize());
    }

    
    public MemorySegment segment() {
        return segment;
    }

    public int width() {
        return (int) VH_WIDTH.get(segment, 0L);
    }

    public int signedness() {
        return (int) VH_SIGNEDNESS.get(segment, 0L);
    }
}
