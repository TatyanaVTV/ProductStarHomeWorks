package ru.vtv.hw.practical.coworking;

import lombok.extern.slf4j.Slf4j;

import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.vtv.hw.practical.coworking.exception.UserException.alreadyRegistered;
import static ru.vtv.hw.practical.coworking.exception.UserException.unknownUser;
import static ru.vtv.hw.practical.coworking.exception.WorkspaceException.*;

@Slf4j
public class CoworkingSystem {
    private final TreeSet<Workspace> workspaces = new TreeSet<>();
    private final TreeMap<User, TreeSet<Workspace>> bookingMap = new TreeMap<>();

    public void addWorkspace(Workspace workspace) {
        if (workspaces.contains(workspace)) throw alreadyExists(workspace);
        workspaces.add(workspace);
        log.debug("Добавлено рабочее место: {}.", workspace);
    }

    public void removeWorkspace(Workspace workspace) {
        if (!workspaces.contains(workspace)) throw unknownWorkspace(workspace);
        workspaces.remove(workspace);
        log.debug("Удалено рабочее место: {}.", workspace);
    }

    public void registerUser(User user) {
        if (bookingMap.containsKey(user)) throw alreadyRegistered(user);
        bookingMap.put(user, new TreeSet<>());
        log.debug("Зарегистрирован новый пользователь: {}", user);
    }

    public void bookWorkspace(User user, Workspace workspace) {
        if (!bookingMap.containsKey(user)) throw unknownUser(user);
        if (!workspaces.contains(workspace)) throw unknownWorkspace(workspace);

        var bookedBy = bookingMap.entrySet().stream()
                .filter(entry -> entry.getValue().contains(workspace))
                .findFirst();
        if (bookedBy.isPresent()) throw alreadyBooked(workspace, bookedBy.get().getKey());

        user.bookWorkspace(workspace);
        bookingMap.get(user).add(workspace);
        log.debug("{} бронирует рабочее место №{}", user, workspace.getNumber());
    }

    public void cancelBooking(User user, Workspace workspace) {
        if (!bookingMap.containsKey(user)) throw unknownUser(user);
        if (!workspaces.contains(workspace)) throw unknownWorkspace(workspace);

        if (!bookingMap.get(user).contains(workspace)) throw isNotBookedByUser(workspace, user);

        user.cancelBooking(workspace);
        bookingMap.get(user).remove(workspace);
        log.debug("{} отменяет бронирование рабочего места №{}", user, workspace.getNumber());
    }

    public void printAvailableWorkingSpaces() {
        System.out.println("Список доступных рабочих мест:");
        AtomicInteger order = new AtomicInteger(1);
        workspaces.stream().filter(Workspace::isAvailable)
                .forEach(workspace -> System.out.printf("%d. %s%n", order.getAndIncrement(), workspace));
    }
}
