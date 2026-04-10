package ru.vtv.hw.practical.coworking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import static java.lang.CharSequence.compare;
import static java.lang.String.format;
import static ru.vtv.hw.practical.coworking.exception.WorkspaceException.alreadyBooked;
import static ru.vtv.hw.practical.coworking.exception.WorkspaceException.isNotBookedByUser;

@Getter
@AllArgsConstructor
public class User implements Comparable<User> {
    private UUID id;
    private String name;
    private String surname;
    private final TreeSet<Workspace> bookedWorkspaces = new TreeSet<>();

    public Set<Workspace> getBookedWorkspaces() {
        return Collections.unmodifiableSet(new TreeSet<>(bookedWorkspaces));
    }

    public void bookWorkspace(Workspace workspace) {
        if (bookedWorkspaces.contains(workspace)) throw alreadyBooked(workspace, this);
        workspace.markAsBooked();
        bookedWorkspaces.add(workspace);
    }

    public void cancelBooking(Workspace workspace) {
        if (!bookedWorkspaces.contains(workspace)) throw isNotBookedByUser(workspace, this);
        workspace.markAsAvailable();
        bookedWorkspaces.remove(workspace);
    }

    @Override
    public int compareTo(@NotNull User o) {
        var compareBySurname = compare(this.surname, o.surname);
        var compareByName = compare(this.name, o.name);
        return compareBySurname != 0 ? compareBySurname : compareByName;
    }

    @Override
    public String toString() {
        return format("%s %s", name, surname);
   }
}
