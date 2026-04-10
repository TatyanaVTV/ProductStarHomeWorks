package ru.vtv.hw.practical.coworking;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import static java.lang.String.format;
import static ru.vtv.hw.practical.coworking.exception.WorkspaceException.alreadyBooked;
import static ru.vtv.hw.practical.coworking.exception.WorkspaceException.alreadyFree;

@Getter
@RequiredArgsConstructor
public class Workspace implements Comparable<Workspace> {
    private final int number;
    private final String type;
    private boolean isAvailable = true;

    public void markAsBooked() {
        if (!isAvailable) throw alreadyBooked(this);
        isAvailable = false;
    }

    public void markAsAvailable() {
        if (isAvailable) throw alreadyFree(this);
        isAvailable = true;
    }

    @Override
    public int compareTo(@NotNull Workspace o) {
        return Integer.compare(number, o.number);
    }

    @Override
    public String toString() {
        return format("№%d тип: %s", number, type);
    }
}
