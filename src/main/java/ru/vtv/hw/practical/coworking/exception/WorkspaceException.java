package ru.vtv.hw.practical.coworking.exception;

import ru.vtv.hw.practical.coworking.User;
import ru.vtv.hw.practical.coworking.Workspace;

import static java.lang.String.format;

public class WorkspaceException extends RuntimeException {
    private WorkspaceException(String message) {
        super(message);
    }

    public static WorkspaceException alreadyExists(Workspace workspace) {
        return new WorkspaceException(format("Workspace '%s' already exist in this CoworkingSystem!", workspace));
    }

    public static WorkspaceException unknownWorkspace(Workspace workspace) {
        return new WorkspaceException(format("Workspace '%s' doesn't belong in this CoworkingSystem!", workspace));
    }

    public static WorkspaceException alreadyFree(Workspace workspace) {
        return new WorkspaceException(format("Workspace '%s' is already free!", workspace));
    }

    public static WorkspaceException isNotBookedByUser(Workspace workspace, User user) {
        return new WorkspaceException(
                format("Workspace '%s' is not booked by user '%s'!", workspace, user)
        );
    }

    public static WorkspaceException alreadyBooked(Workspace workspace) {
        return new WorkspaceException(format("Workspace '%s' is already booked!", workspace));
    }

    public static WorkspaceException alreadyBooked(Workspace workspace, User user) {
        return new WorkspaceException(
                format("Workspace '%s' is already booked by user '%s' (id = %s)!", workspace, user, user.getId())
        );
    }
}
