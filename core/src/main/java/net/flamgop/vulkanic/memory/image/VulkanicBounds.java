package net.flamgop.vulkanic.memory.image;

import net.flamgop.vulkanic.math.Int3;
import org.jetbrains.annotations.NotNull;

/// @apiNote the order of lower and upper is not necessarily enforced nor does it really matter in most cases (i.e., lower > upper is usually valid)
public record VulkanicBounds(@NotNull Int3 lower, @NotNull Int3 upper) {
}
