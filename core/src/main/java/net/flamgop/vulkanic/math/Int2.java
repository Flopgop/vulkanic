package net.flamgop.vulkanic.math;

import java.nio.IntBuffer;

public interface Int2 {
    int x();
    int y();

    record Impl(int x, int y) implements Int2 {
        public Impl(IntBuffer buffer) {
            this(buffer.get(buffer.position()), buffer.get(buffer.position() + 1));
        }
    }

    static Int2 fromJoml(org.joml.Vector2ic vec) {
        return new Impl(vec.x(), vec.y());
    }
}
