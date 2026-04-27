package ru.vtv.hw.practical.niofile.menu;

import ru.vtv.hw.practical.niofile.UserManager;

public interface UserManagerCommand {
    String getName();
    boolean execute(UserManager userManager, InputProvider inputProvider);
}
