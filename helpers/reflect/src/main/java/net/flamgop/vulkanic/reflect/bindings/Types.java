package net.flamgop.vulkanic.reflect.bindings;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.ValueLayout;

final class Types {
    public static final MemoryLayout CSTRING = unboundedArray(ValueLayout.JAVA_BYTE);

    public static MemoryLayout unboundedArray(MemoryLayout layout) {
        return MemoryLayout.sequenceLayout(Long.MAX_VALUE, layout);
    }
}
