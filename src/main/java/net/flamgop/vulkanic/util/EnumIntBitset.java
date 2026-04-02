package net.flamgop.vulkanic.util;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public record EnumIntBitset<T extends Enum<T> & Bitmaskable<Integer>>(Integer mask) implements Bitset<Integer> {

    public EnumIntBitset(@NotNull T[] constants) {
        int result = 0;
        for (T constant : constants) {
            result |= constant.flag();
        }
        this(result);
    }

    @ApiStatus.Internal
    public EnumIntBitset {}

    @SafeVarargs
    public static <T extends Enum<T> & Bitmaskable<Integer>> EnumIntBitset<T> of(T... elements) {
        return new EnumIntBitset<>(elements);
    }

    public static <T extends Enum<T> & Bitmaskable<Integer>> EnumIntBitset<T> all() {
        return new EnumIntBitset<>(Integer.MAX_VALUE);
    }

    public boolean contains(@NotNull T constant) {
        return (this.mask & constant.flag()) != 0;
    }

    public boolean containsAll(@NotNull EnumIntBitset<T> other) {
        return (this.mask & other.mask()) == other.mask();
    }

    public EnumIntBitset<T> union(@NotNull EnumIntBitset<T> other) {
        return new EnumIntBitset<>(this.mask | other.mask());
    }

    public EnumIntBitset<T> intersect(@NotNull EnumIntBitset<T> other) {
        return new EnumIntBitset<>(this.mask & other.mask());
    }

    public EnumIntBitset<T> exclude(@NotNull EnumIntBitset<T> other) {
        return new EnumIntBitset<>(this.mask & ~other.mask());
    }

    public boolean none() {
        return mask == 0;
    }

    public boolean some() {
        return mask != 0;
    }

    @Override
    public @NotNull String toString() {
        return "EnumIntBitset(0x" + Integer.toHexString(mask).toUpperCase() + ")";
    }
}
