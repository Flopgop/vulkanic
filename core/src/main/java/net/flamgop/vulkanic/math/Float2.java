package net.flamgop.vulkanic.math;

import java.nio.FloatBuffer;

public interface Float2 {
    float x();
    float y();

    record Impl(float x, float y) implements Float2 {
        public Impl(FloatBuffer buffer) {
            this(buffer.get(buffer.position()), buffer.get(buffer.position() + 1));
        }
    }

    static Float2 fromJoml(org.joml.Vector2fc vec) {
        return new Impl(vec.x(), vec.y());
    }
}
