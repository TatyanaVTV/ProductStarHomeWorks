package ru.vtv.hw.practical.adventurer;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

import static ru.vtv.hw.practical.adventurer.Action.EXIT;
import static ru.vtv.hw.practical.adventurer.Action.ERROR;

@Slf4j
public class AdventurerInventoryCheck {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final AdventurerInventoryCommandHandler INVENTORY_HANDLER = new AdventurerInventoryCommandHandler();

    public static void main(String[] args) {
        while (true) {
            INVENTORY_HANDLER.showMenu();

            var command = readCommand();
            if (EXIT.equals(command.getAction())) {
                System.out.println("До новых встречи!");
                return;
            } else {
                try {
                    INVENTORY_HANDLER.processCommand(command);
                } catch (AdventurerInventoryException e) {
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
