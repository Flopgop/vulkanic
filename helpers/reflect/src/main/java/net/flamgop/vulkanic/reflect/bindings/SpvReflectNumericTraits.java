package net.flamgop.vulkanic.reflect.bindings;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

import static java.lang.foreign.MemoryLayout.PathElement;

public final class SpvReflectNumericTraits {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            SpvReflectNumericTraits_Scalar.LAYOUT.withName("scalar"),
            SpvReflectNumericTraits_Vector.LAYOUT.withName("vector"),
            SpvReflectNumericTraits_Matrix.LAYOUT.withName("matrix")
    );

    private static final long SCALAR_OFFSET = LAYOUT.byteOffset(PathElement.groupElement("scalar"));
    private static final long VECTOR_OFFSET = LAYOUT.byteOffset(PathElement.groupElement("vector"));
    private static final long MATRIX_OFFSET = LAYOUT.byteOffset(PathElement.groupElement("matrix"));

    private final MemorySegment segment;

    
    public SpvReflectNumericTraits(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectNumericTraits(MemorySegment segment) {
        this.segment = segment.reinterpret(LAYOUT.byteSize());
    }

    
    public MemorySegment segment() {
        return segment;
    }

    public SpvReflectNumericTraits_Scalar scalar() {
        long size = SpvReflectNumericTraits_Scalar.LAYOUT.byteSize();
        return new SpvReflectNumericTraits_Scalar(segment.asSlice(SCALAR_OFFSET, size));
    }

    public SpvReflectNumericTraits_Vector vector() {
        long size = SpvReflectNumericTraits_Vector.LAYOUT.byteSize();
        return new SpvReflectNumericTraits_Vector(segment.asSlice(VECTOR_OFFSET, size));
    }

    public SpvReflectNumericTraits_Matrix matrix() {
        long size = SpvReflectNumericTraits_Matrix.LAYOUT.byteSize();
        return new SpvReflectNumericTraits_Matrix(segment.asSlice(MATRIX_OFFSET, size));
    }
}
