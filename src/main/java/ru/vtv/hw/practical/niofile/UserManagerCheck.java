package ru.vtv.hw.practical.niofile;

import lombok.extern.slf4j.Slf4j;
import ru.vtv.hw.practical.niofile.menu.Action;
import ru.vtv.hw.practical.niofile.menu.Command;
import ru.vtv.hw.practical.niofile.menu.UserManagerCommandHandler;

import java.util.Scanner;

import static ru.vtv.hw.practical.niofile.menu.Action.ERROR;
import static ru.vtv.hw.practical.niofile.menu.Action.EXIT;

@Slf4j
public class UserManagerCheck {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final UserManagerCommandHandler USER_MANAGER_HANDLER = new UserManagerCommandHandler();

    public static void main(String[] args) {
        while (true) {
            USER_MANAGER_HANDLER.showMenu();

            var command = readCommand();
            if (EXIT.equals(command.getAction())) {
                System.out.println("Выход из программы.");
                return;
            } else {
                try {
                    USER_MANAGER_HANDLER.processCommand(command);
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
        }
    }

    private static Command readCommand() {
        try {
            var code = SCANNER.nextLine();
            var actionCode = Integer.valueOf(code);
            var action = Action.fromCode(actionCode);
            return new Command(action);
        } catch (Exception ex) {
            System.out.printf("Проблема обработки ввода. %s\n", ex.getLocalizedMessage());
        }
        return new Command(ERROR);
    }
}
