package net.flamgop.vulkanic.math;

import java.nio.IntBuffer;

public interface Int3 {
    int x();
    int y();
    int z();

    record Impl(int x, int y, int z) implements Int3 {
        public Impl(IntBuffer buffer) {
            this(buffer.get(buffer.position()), buffer.get(buffer.position() + 1), buffer.get(buffer.position() + 2));
        }
    }

    static Int3 fromJoml(org.joml.Vector3ic vec) {
        return new Impl(vec.x(), vec.y(), vec.z());
    }
}
