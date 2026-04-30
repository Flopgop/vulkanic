package net.flamgop.vulkanic.memory;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VulkanicDeviceSize implements Comparable<VulkanicDeviceSize>, Serializable {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.####", new DecimalFormatSymbols(Locale.ROOT));
    private static final Pattern PARSE_PATTERN = Pattern.compile("^(\\d+)\\s*(B|KB|MB|GB|TB|PB|KIB|MIB|GIB|TIB|PIB)$", Pattern.CASE_INSENSITIVE);

    public static VulkanicDeviceSize parse(String text) {
        Matcher matcher = PARSE_PATTERN.matcher(text.trim().toUpperCase());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse: " + text);
        }
        long scalar = Long.parseLong(matcher.group(1));
        return switch (matcher.group(2)) {
            case "B"   -> ofBytes(scalar);
            case "KB"  -> ofKilobytes(scalar);
            case "MB"  -> ofMegabytes(scalar);
            case "GB"  -> ofGigabytes(scalar);
            case "TB"  -> ofTerabytes(scalar);
            case "PB"  -> ofPetabytes(scalar);
            case "KIB" -> ofKibibytes(scalar);
            case "MIB" -> ofMebibytes(scalar);
            case "GIB" -> ofGibibytes(scalar);
            case "TIB" -> ofTebibytes(scalar);
            case "PIB" -> ofPebibytes(scalar);
            default    -> throw new IllegalStateException("Unexpected unit");
        };
    }

    public static VulkanicDeviceSize ofBytes(long bytes) { return new VulkanicDeviceSize(bytes); }

    /// SI units
    public static VulkanicDeviceSize ofKilobytes(long kb) { return new VulkanicDeviceSize(kb * 1000L); }
    public static VulkanicDeviceSize ofMegabytes(long mb) { return new VulkanicDeviceSize(mb * 1000_000L); }
    public static VulkanicDeviceSize ofGigabytes(long gb) { return new VulkanicDeviceSize(gb * 1000_000_000L); }
    public static VulkanicDeviceSize ofTerabytes(long tb) { return new VulkanicDeviceSize(tb * 1000_000_000_000L); }
    public static VulkanicDeviceSize ofPetabytes(long pb) { return new VulkanicDeviceSize(pb * 1000_000_000_000_000L); }

    /// IEC units
    public static VulkanicDeviceSize ofKibibytes(long kib) { return new VulkanicDeviceSize(kib << 10); }
    public static VulkanicDeviceSize ofMebibytes(long mib) { return new VulkanicDeviceSize(mib << 20); }
    public static VulkanicDeviceSize ofGibibytes(long gib) { return new VulkanicDeviceSize(gib << 30); }
    public static VulkanicDeviceSize ofTebibytes(long tib) { return new VulkanicDeviceSize(tib << 40); }
    public static VulkanicDeviceSize ofPebibytes(long pib) { return new VulkanicDeviceSize(pib << 50); }

    private final long bytes;

    private VulkanicDeviceSize(long bytes) {
        this.bytes = bytes;
    }

    public long bytes() {
        return bytes;
    }

    @Override
    public String toString() {
        if (bytes == 0) return "0 B";
        if (bytes < 1000) return bytes + " B";

        final long[] values = {
                1_000_000_000_000_000L,
                1_000_000_000_000L,
                1_000_000_000L,
                1_000_000L,
                1_000L
        };
        final String[] units = { "PB", "TB", "GB", "MB", "KB" };

        for (int i = 0; i < values.length; i++) {
            if (bytes >= values[i]) {
                double result = (double) bytes / values[i];
                return FORMAT.format(result) + " " + units[i];
            }
        }

        return bytes + " B";
    }

    @Override
    public int compareTo(@NotNull VulkanicDeviceSize o) {
        return Long.compare(bytes, o.bytes);
    }

    @Override
    public int hashCode() {
        return Long.hashCode(bytes);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof VulkanicDeviceSize size) return bytes == size.bytes;
        if (obj instanceof Number num) return bytes == num.longValue();
        return false;
    }
}
