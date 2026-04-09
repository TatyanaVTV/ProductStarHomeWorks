package ru.vtv.hw.practical.cybersport;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static java.lang.String.format;
import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Data
public class Player implements Comparable<Player> {
    private final int id;
    private final String name;
    private int rating = 0;

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (isNull(obj)) return false;
        if (obj.getClass() != Player.class) return false;

        var other = (Player) obj;
        return id == other.id && name.equals(other.name);
    }

    @Override
    public int compareTo(@NotNull Player o) {
        int rankComparison = Integer.compare(rating, o.rating);
        if (rankComparison != 0) return rankComparison;
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return format("%s (id = %s)", name, id);
    }
}
