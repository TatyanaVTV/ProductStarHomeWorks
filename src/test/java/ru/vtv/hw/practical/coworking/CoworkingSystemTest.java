package ru.vtv.hw.practical.coworking;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.vtv.hw.practical.coworking.exception.UserException;
import ru.vtv.hw.practical.coworking.exception.WorkspaceException;

import java.util.TreeSet;
import java.util.UUID;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

public class CoworkingSystemTest {

    private static final String USER_NAME = "Иван";
    private static final String USER_SURNAME = "Иванов";
    private static final String WORKSPACE_TYPE = "Офисное";
    private static final int WORKSPACE_NUMBER = 1;

    @Test
    void addWorkspace_shouldAddNewWorkspace() {
        var coworking = new CoworkingSystem();
        var workspace = new Workspace(WORKSPACE_NUMBER, WORKSPACE_TYPE);

        coworking.addWorkspace(workspace);

        assertTrue(getWorkspacesViaReflection(coworking).contains(workspace));
    }

    @Test
    void addWorkspace_shouldThrowExceptionWhenWorkspaceAlreadyExists() {
        var coworking = new CoworkingSystem();
        var workspace = new Workspace(WORKSPACE_NUMBER, WORKSPACE_TYPE);
        coworking.addWorkspace(workspace);

        var exception = assertThrows(WorkspaceException.class, () -> coworking.addWorkspace(workspace));
        assertEquals(format("Workspace '%s' already exist in this CoworkingSystem!", workspace), exception.getMessage());
    }

    @Test
    void removeWorkspace_shouldRemoveExistingWorkspace() {
        var coworking = new CoworkingSystem();
        var workspace = new Workspace(WORKSPACE_NUMBER, WORKSPACE_TYPE);
        coworking.addWorkspace(workspace);

        coworking.removeWorkspace(workspace);

        assertFalse(getWorkspacesViaReflection(coworking).contains(workspace));
    }

    @Test
    void removeWorkspace_shouldThrowExceptionWhenWorkspaceUnknown() {
        var coworking = new CoworkingSystem();
        var workspace = new Workspace(WORKSPACE_NUMBER, WORKSPACE_TYPE);

        var exception = assertThrows(WorkspaceException.class, () -> coworking.removeWorkspace(workspace));
        assertEquals(format("Workspace '%s' doesn't belong in this CoworkingSystem!", workspace), exception.getMessage());
    }

    @Test
    void registerUser_shouldRegisterNewUser() {
        var coworking = new CoworkingSystem();
        var user = new User(UUID.randomUUID(), USER_NAME, USER_SURNAME);

        coworking.registerUser(user);

        assertTrue(getBookingMapViaReflection(coworking).containsKey(user));
    }

    @Test
    void registerUser_shouldThrowExceptionWhenUserAlreadyRegistered() {
        var coworking = new CoworkingSystem();
        var user = new User(UUID.randomUUID(), USER_NAME, USER_SURNAME);
        coworking.registerUser(user);

        var exception = assertThrows(UserException.class, () -> coworking.registerUser(user));
        assertEquals(format("User '%s' (id = %s) is already registered in this CoworkingSystem!", user, user.getId()), exception.getMessage());
    }

    @Test
    void bookWorkspace_shouldBookWorkspaceForUser() {
        var coworking = new CoworkingSystem();
        var user = new User(UUID.randomUUID(), USER_NAME, USER_SURNAME);
        var workspace = new Workspace(WORKSPACE_NUMBER, WORKSPACE_TYPE);
        coworking.registerUser(user);
        coworking.addWorkspace(workspace);

        coworking.bookWorkspace(user, workspace);

        assertTrue(user.getBookedWorkspaces().contains(workspace));
        assertFalse(workspace.isAvailable());
    }

    @Test
    void bookWorkspace_shouldThrowExceptionWhenUserUnknown() {
        var coworking = new CoworkingSystem();
        var user = new User(UUID.randomUUID(), USER_NAME, USER_SURNAME);
        var workspace = new Workspace(WORKSPACE_NUMBER, WORKSPACE_TYPE);
        coworking.addWorkspace(workspace);

        var exception = assertThrows(UserException.class, () -> coworking.bookWorkspace(user, workspace));
        assertEquals(format("User '%s' (id = %s) isn't registered in this CoworkingSystem!", user, user.getId()), exception.getMessage());
    }

    @Test
    void bookWorkspace_shouldThrowExceptionWhenWorkspaceUnknown() {
        var coworking = new CoworkingSystem();
        var user = new User(UUID.randomUUID(), USER_NAME, USER_SURNAME);
        var workspace = new Workspace(WORKSPACE_NUMBER, WORKSPACE_TYPE);
        coworking.registerUser(user);

        var exception = assertThrows(WorkspaceException.class, () -> coworking.bookWorkspace(user, workspace));
        assertEquals(format("Workspace '%s' doesn't belong in this CoworkingSystem!", workspace), exception.getMessage());
    }

    @Test
    void bookWorkspace_shouldThrowExceptionWhenWorkspaceAlreadyBooked() {
        var coworking = new CoworkingSystem();
        var user1 = new User(UUID.randomUUID(), "Пётр", "Петров");
        var user2 = new User(UUID.randomUUID(), USER_NAME, USER_SURNAME);
        var workspace = new Workspace(WORKSPACE_NUMBER, WORKSPACE_TYPE);
        coworking.registerUser(user1);
        coworking.registerUser(user2);
        coworking.addWorkspace(workspace);
        coworking.bookWorkspace(user1, workspace);

        var exception = assertThrows(WorkspaceException.class, () -> coworking.bookWorkspace(user2, workspace));
        assertTrue(exception.getMessage().contains(format("Workspace '%s' is already booked by user", workspace)));
    }

    @Test
    void markAsBooked_shouldThrowExceptionWhenWorkspaceAlreadyBooked() {
        var workspace = new Workspace(WORKSPACE_NUMBER, WORKSPACE_TYPE);
        workspace.markAsBooked();
        var exception = assertThrows(WorkspaceException.class, workspace::markAsBooked);
        assertTrue(exception.getMessage().contains(format("Workspace '%s' is already booked!", workspace)));
    }

    @Test
    void markAsAvailable_shouldThrowExceptionWhenWorkspaceAlreadyBooked() {
        var workspace = new Workspace(WORKSPACE_NUMBER, WORKSPACE_TYPE);
        var exception = assertThrows(WorkspaceException.class, workspace::markAsAvailable);
        assertTrue(exception.getMessage().contains(format("Workspace '%s' is already free!", workspace)));
    }

    @Test
    void bookWorkspace_shouldThrowExceptionWhenWorkspaceAlreadyBooked_SameUser() {
        var coworking = new CoworkingSystem();
        var user = new User(UUID.randomUUID(), USER_NAME, USER_SURNAME);
        var workspace = new Workspace(WORKSPACE_NUMBER, WORKSPACE_TYPE);
        coworking.registerUser(user);
        coworking.addWorkspace(workspace);
        coworking.bookWorkspace(user, workspace);

        var exception = assertThrows(WorkspaceException.class, () -> coworking.bookWorkspace(user, workspace));
        assertTrue(exception.getMessage().contains(format("Workspace '%s' is already booked by user", workspace)));
    }

    @Test
    void bookWorkspace_shouldThrowExceptionWhenWorkspaceAlreadyBooked_SameUser2() {
        var user = new User(UUID.randomUUID(), USER_NAME, USER_SURNAME);
        var workspace = new Workspace(WORKSPACE_NUMBER, WORKSPACE_TYPE);
        user.bookWorkspace(workspace);

        var exception = assertThrows(WorkspaceException.class, () -> user.bookWorkspace(workspace));
        assertTrue(exception.getMessage().contains(
                format("Workspace '%s' is already booked by user '%s' (id = %s)!", workspace, user, user.getId()))
        );
    }

    @Test
    void cancelBooking_shouldCancelBooking() {
        var coworking = new CoworkingSystem();
        var user = new User(UUID.randomUUID(), USER_NAME, USER_SURNAME);
        var workspace = new Workspace(WORKSPACE_NUMBER, WORKSPACE_TYPE);
        coworking.registerUser(user);
        coworking.addWorkspace(workspace);
        coworking.bookWorkspace(user, workspace);

        coworking.cancelBooking(user, workspace);

        assertFalse(user.getBookedWorkspaces().contains(workspace));
        assertTrue(workspace.isAvailable());
    }

    @Test
    void cancelBooking_shouldThrowExceptionWhenBookingDoesNotExist() {
        var coworking = new CoworkingSystem();
        var user = new User(UUID.randomUUID(), USER_NAME, USER_SURNAME);
        var workspace = new Workspace(WORKSPACE_NUMBER, WORKSPACE_TYPE);
        coworking.registerUser(user);
        coworking.addWorkspace(workspace);

        var exception = assertThrows(WorkspaceException.class, () -> coworking.cancelBooking(user, workspace));
        assertEquals(format("Workspace '%s' is not booked by user '%s'!", workspace, user), exception.getMessage());
    }

    @Test
    void cancelBooking_shouldThrowExceptionWhenUnknownWorkspace() {
        var coworking = new CoworkingSystem();
        var user = new User(UUID.randomUUID(), USER_NAME, USER_SURNAME);
        var workspace = new Workspace(WORKSPACE_NUMBER, WORKSPACE_TYPE);
        coworking.registerUser(user);

        var exception = assertThrows(WorkspaceException.class, () -> coworking.cancelBooking(user, workspace));
        assertEquals(format("Workspace '%s' doesn't belong in this CoworkingSystem!", workspace), exception.getMessage());
    }

    @Test
    void cancelBooking_shouldThrowExceptionWhenUnknownUser() {
        var coworking = new CoworkingSystem();
        var user = new User(UUID.randomUUID(), USER_NAME, USER_SURNAME);
        var workspace = new Workspace(WORKSPACE_NUMBER, WORKSPACE_TYPE);
        coworking.addWorkspace(workspace);

        var exception = assertThrows(UserException.class, () -> coworking.cancelBooking(user, workspace));
        assertEquals(
                format("User '%s' (id = %s) isn't registered in this CoworkingSystem!", user, user.getId()),
                exception.getMessage()
        );
    }

    @Test
    void cancelBooking_shouldThrowExceptionWhenBookingDoesNotExist2() {
        var user = new User(UUID.randomUUID(), USER_NAME, USER_SURNAME);
        var workspace = new Workspace(WORKSPACE_NUMBER, WORKSPACE_TYPE);

        var exception = assertThrows(WorkspaceException.class, () -> user.cancelBooking(workspace));
        assertEquals(format("Workspace '%s' is not booked by user '%s'!", workspace, user), exception.getMessage());
    }

    @SneakyThrows
    @Test
    void printAvailableWorkingSpaces_shouldPrintAvailableWorkspaces() {
        var coworking = new CoworkingSystem();
        var workspace1 = new Workspace(1, WORKSPACE_TYPE);
        var workspace2 = new Workspace(2, WORKSPACE_TYPE);
        coworking.addWorkspace(workspace1);
        coworking.addWorkspace(workspace2);
        workspace2.markAsBooked();

        coworking.printAvailableWorkingSpaces();

        var output = tapSystemOut(coworking::printAvailableWorkingSpaces);
        output = output.replaceAll("\\r\\n|\\r", "\n");

        assertTrue(output.contains("Список доступных рабочих мест:"),
                "Вывод должен содержать заголовок списка");
        assertTrue(output.contains("1. №1 тип: Офисное"),
                "Должен отображаться доступный workspace №1");
        assertFalse(output.contains("№2 тип: Офисное"),
                "Занятое рабочее место №2 не должно отображаться в списке доступных");
        assertTrue(output.startsWith("Список доступных рабочих мест:\n1. №1 тип: Офисное"),
                "Нумерация должна начинаться с 1 и идти по порядку");
        assertFalse(output.isBlank(), "Вывод не должен быть пустым");
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private TreeSet<Workspace> getWorkspacesViaReflection(CoworkingSystem coworking) {
        var workspacesField = CoworkingSystem.class.getDeclaredField("workspaces");
        workspacesField.setAccessible(true);
        return (TreeSet<Workspace>) workspacesField.get(coworking);
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private java.util.TreeMap<User, TreeSet<Workspace>> getBookingMapViaReflection(CoworkingSystem coworking) {
        var bookingMapField = CoworkingSystem.class.getDeclaredField("bookingMap");
        bookingMapField.setAccessible(true);
        return (java.util.TreeMap<User, TreeSet<Workspace>>) bookingMapField.get(coworking);
    }
}
