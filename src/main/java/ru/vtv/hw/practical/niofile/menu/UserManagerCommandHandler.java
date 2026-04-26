package ru.vtv.hw.practical.niofile.menu;

import lombok.extern.slf4j.Slf4j;
import ru.vtv.hw.practical.niofile.UserManager;
import ru.vtv.hw.practical.niofile.menu.commands.AddUserCommand;
import ru.vtv.hw.practical.niofile.menu.commands.ExitCommand;
import ru.vtv.hw.practical.niofile.menu.commands.ShowAllUsersCommand;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static ru.vtv.hw.practical.niofile.menu.Action.*;

@Slf4j
public class UserManagerCommandHandler {
    private static final UserManager USER_MANAGER = new UserManager();
    private static final Map<Action, UserManagerCommand> COMMAND_MAP = new HashMap<>();
    private static final InputProvider INPUT_PROVIDER = new ConsoleInputProvider();

    static {
        registerCommands();
    }

    public void processCommand(Command command) {
        var action = command.getAction();

        if (EXIT.equals(action)) {
            System.out.println("Выход");
            return;
        }

        var userManagerCommand = COMMAND_MAP.get(action);

        if (isNull(userManagerCommand)) {
            System.out.printf("Action %s is not supported.%n", action);
            return;
        }

        try {
            userManagerCommand.execute(USER_MANAGER, INPUT_PROVIDER);
        } finally {
            log.debug("Processing command. Action: {}", command.getAction().name());
        }
    }

    public void showMenu() {
        System.out.println("----------------------");
        System.out.println("Добро пожаловать в интерфейс Управления Пользователями!");
        System.out.println("Выберите действие:");

        System.out.printf("%d. %s%n", ADD_USER.getCode(), COMMAND_MAP.get(ADD_USER).getName());
        System.out.printf("%d. %s%n", SHOW_ALL_USERS.getCode(), COMMAND_MAP.get(SHOW_ALL_USERS).getName());
        System.out.printf("%d. %s%n", EXIT.getCode(), COMMAND_MAP.get(EXIT).getName());

        System.out.println("----------------------");
    }

    private static void registerCommands() {
        COMMAND_MAP.put(ADD_USER, new AddUserCommand());
        COMMAND_MAP.put(SHOW_ALL_USERS, new ShowAllUsersCommand());
        COMMAND_MAP.put(EXIT, new ExitCommand());
    }
}
