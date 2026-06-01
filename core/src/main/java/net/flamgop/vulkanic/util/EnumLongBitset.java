package net.flamgop.vulkanic.util;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static <T extends Enum<T> & Bitmaskable<Long>> EnumLongBitset<T> empty() {
        return new EnumLongBitset<>(0L);
    }

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

    public void require(@NotNull T constant) {
        if (!contains(constant)) {
            throw new IllegalStateException("EnumIntBitset does not contain " + constant);
        }
    }

    public void requireAll(@NotNull EnumLongBitset<T> other) {
        if (!containsAll(other)) {
            throw new IllegalStateException("EnumIntBitset does not contain all " + other);
        }
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

    public @NotNull String toFriendlyString(Class<T> clazz) {
        return Stream.of(clazz.getEnumConstants())
                .filter(this::contains)
                .map(Enum::name)
                .collect(Collectors.joining("|"));
    }

    @Override
    public @NotNull String toString() {
        return "EnumLongBitset(0x" + Long.toHexString(mask).toUpperCase() + ")";
    }
}
