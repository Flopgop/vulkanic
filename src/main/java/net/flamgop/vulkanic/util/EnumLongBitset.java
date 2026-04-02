package net.flamgop.vulkanic.util;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public record EnumLongBitset<T extends Enum<T> & Bitmaskable<Long>>(Long mask) implements Bitset<Long> {

    public EnumLongBitset(@NotNull T[] constants) {
        long result = 0;
        for (T constant : constants) {
            result |= constant.flag();
        }
        this(result);
    }

    @ApiStatus.Internal
    public EnumLongBitset {}

    @SafeVarargs
    public static <T extends Enum<T> & Bitmaskable<Long>> EnumLongBitset<T> of(T... elements) {
        return new EnumLongBitset<>(elements);
    }

    public static <T extends Enum<T> & Bitmaskable<Long>> EnumLongBitset<T> all() {
        return new EnumLongBitset<>(Long.MAX_VALUE);
    }

    public boolean contains(@NotNull T constant) {
        return (this.mask & constant.flag()) != 0;
    }

    public boolean containsAll(@NotNull EnumLongBitset<T> other) {
        return (this.mask & other.mask()) == other.mask();
    }

    public EnumLongBitset<T> union(@NotNull EnumLongBitset<T> other) {
        return new EnumLongBitset<>(this.mask | other.mask());
    }

    public EnumLongBitset<T> intersect(@NotNull EnumLongBitset<T> other) {
        return new EnumLongBitset<>(this.mask & other.mask());
    }

    public EnumLongBitset<T> exclude(@NotNull EnumLongBitset<T> other) {
        return new EnumLongBitset<>(this.mask & ~other.mask());
    }

    public boolean none() {
        return mask == 0;
    }

    public boolean some() {
        return mask != 0;
    }

    @Override
    public @NotNull String toString() {
        return "EnumLongBitset(0x" + Long.toHexString(mask).toUpperCase() + ")";
    }
}
