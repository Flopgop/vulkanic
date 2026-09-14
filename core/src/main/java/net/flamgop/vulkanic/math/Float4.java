package net.flamgop.vulkanic.math;

import java.nio.FloatBuffer;

public interface Float4 {
    float x();
    float y();
    float z();
    float w();

    record Impl(float x, float y, float z, float w) implements Float4 {
        public Impl(FloatBuffer buffer) {
            this(buffer.get(buffer.position()), buffer.get(buffer.position() + 1), buffer.get(buffer.position() + 2), buffer.get(buffer.position() + 3));
        }
    }

    static Float4 fromJoml(org.joml.Vector4fc vec) {
        return new Float4.Impl(vec.x(), vec.y(), vec.z(), vec.w());
    }
}
