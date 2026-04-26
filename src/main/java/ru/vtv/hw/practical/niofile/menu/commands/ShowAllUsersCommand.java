package ru.vtv.hw.practical.niofile.menu.commands;

import ru.vtv.hw.practical.niofile.UserManager;
import ru.vtv.hw.practical.niofile.menu.InputProvider;
import ru.vtv.hw.practical.niofile.menu.UserManagerCommand;

public class ShowAllUsersCommand implements UserManagerCommand {
    @Override
    public String getName() {
        return "Показать список пользователей";
    }

    @Override
    public boolean execute(UserManager userManager, InputProvider inputProvider) {
        System.out.println("Список пользователей: ");
        userManager.getUsers().forEach(System.out::println);
        if (userManager.getUsers().isEmpty()) {
            System.out.println("<пусто>");
        }
        return true;
    }
}
