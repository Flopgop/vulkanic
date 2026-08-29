package net.flamgop.vulkanic.reflect.bindings;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.VarHandle;

import static java.lang.foreign.MemoryLayout.PathElement;

public final class SpvReflectNumericTraits_Matrix {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("column_count"),
            ValueLayout.JAVA_INT.withName("row_count"),
            ValueLayout.JAVA_INT.withName("stride")
    );

    private static final VarHandle VH_COLUMN_COUNT = LAYOUT.varHandle(PathElement.groupElement("column_count"));
    private static final VarHandle VH_ROW_COUNT = LAYOUT.varHandle(PathElement.groupElement("row_count"));
    private static final VarHandle VH_STRIDE = LAYOUT.varHandle(PathElement.groupElement("stride"));

    private final MemorySegment segment;

    
    public SpvReflectNumericTraits_Matrix(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectNumericTraits_Matrix(MemorySegment segment) {
        this.segment = segment.reinterpret(LAYOUT.byteSize());
    }

    
    public MemorySegment segment() {
        return segment;
    }

    public int columnCount() {
        return (int) VH_COLUMN_COUNT.get(segment, 0L);
    }

    public int rowCount() {
        return (int) VH_ROW_COUNT.get(segment, 0L);
    }

    public int stride() {
        return (int) VH_STRIDE.get(segment, 0L);
    }
}
