package net.flamgop.vulkanic.reflect.bindings;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.VarHandle;

import static java.lang.foreign.MemoryLayout.PathElement;

public final class SpvReflectImageTraits {

    public static final MemoryLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("dim"),
            ValueLayout.JAVA_INT.withName("depth"),
            ValueLayout.JAVA_INT.withName("arrayed"),
            ValueLayout.JAVA_INT.withName("ms"),
            ValueLayout.JAVA_INT.withName("sampled"),
            ValueLayout.JAVA_INT.withName("image_format")
    );

    private static final VarHandle VH_DIM = LAYOUT.varHandle(PathElement.groupElement("dim"));
    private static final VarHandle VH_DEPTH = LAYOUT.varHandle(PathElement.groupElement("depth"));
    private static final VarHandle VH_ARRAYED = LAYOUT.varHandle(PathElement.groupElement("arrayed"));
    private static final VarHandle VH_MS = LAYOUT.varHandle(PathElement.groupElement("ms"));
    private static final VarHandle VH_SAMPLED = LAYOUT.varHandle(PathElement.groupElement("sampled"));
    private static final VarHandle VH_IMAGE_FORMAT = LAYOUT.varHandle(PathElement.groupElement("image_format"));

    private final MemorySegment segment;

    
    public SpvReflectImageTraits(Arena arena) {
        this.segment = arena.allocate(LAYOUT);
    }

    
    public SpvReflectImageTraits(MemorySegment segment) {
        this.segment = segment.reinterpret(LAYOUT.byteSize());
    }

    
    public MemorySegment segment() {
        return segment;
    }

    public SpvDim dim() {
        return SpvDim.fromValue((int) VH_DIM.get(segment, 0L));
    }

    public int depth() {
        return (int) VH_DEPTH.get(segment, 0L);
    }

    public int arrayed() {
        return (int) VH_ARRAYED.get(segment, 0L);
    }

    public int ms() {
        return (int) VH_MS.get(segment, 0L);
    }

    public int sampled() {
        return (int) VH_SAMPLED.get(segment, 0L);
    }

    public SpvImageFormat imageFormat() {
        return SpvImageFormat.fromValue((int) VH_IMAGE_FORMAT.get(segment, 0L));
    }
}
