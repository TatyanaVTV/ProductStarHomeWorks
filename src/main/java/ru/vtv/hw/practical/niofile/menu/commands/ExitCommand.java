package ru.vtv.hw.practical.niofile.menu.commands;

import ru.vtv.hw.practical.niofile.UserManager;
import ru.vtv.hw.practical.niofile.menu.InputProvider;
import ru.vtv.hw.practical.niofile.menu.UserManagerCommand;

public class ExitCommand implements UserManagerCommand {
    @Override
    public String getName() {
        return "Выход";
    }

    @Override
    public boolean execute(UserManager userManager, InputProvider inputProvider) {
        return true;
    }
}
