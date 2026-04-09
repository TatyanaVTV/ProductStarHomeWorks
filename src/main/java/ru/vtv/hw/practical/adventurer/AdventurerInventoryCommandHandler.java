package ru.vtv.hw.practical.adventurer;

import lombok.extern.slf4j.Slf4j;
import ru.vtv.hw.practical.adventurer.commands.*;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static ru.vtv.hw.practical.adventurer.Action.*;

@Slf4j
public class AdventurerInventoryCommandHandler {
    private static final AdventurerInventory ADVENTURER_INVENTORY = new AdventurerInventory();
    private static final Map<Action, InventoryCommand> COMMAND_MAP = new HashMap<>();
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

        var inventoryCommand = COMMAND_MAP.get(action);

        if (isNull(inventoryCommand)) {
            System.out.printf("Действие %s не поддерживается.\n", action);
            return;
        }

        try {
            inventoryCommand.execute(ADVENTURER_INVENTORY, INPUT_PROVIDER);
        } finally {
            log.debug("Обработка команды. Действие: {}", command.getAction().name());
        }
    }

    public void showMenu() {
        System.out.println("----------------------");
        System.out.println("Добро пожаловать в Инвентарь приключенца!");
        System.out.println("Выберите действие:");

        System.out.printf("%d. %s%n", ADD_ITEM.getCode(), COMMAND_MAP.get(ADD_ITEM).getName());
        System.out.printf("%d. %s%n", CHANGE_ITEM_COUNT.getCode(), COMMAND_MAP.get(CHANGE_ITEM_COUNT).getName());
        System.out.printf("%d. %s%n", REMOVE_ITEM.getCode(), COMMAND_MAP.get(REMOVE_ITEM).getName());
        System.out.printf("%d. %s%n", SEARCH_ITEM.getCode(), COMMAND_MAP.get(SEARCH_ITEM).getName());
        System.out.printf("%d. %s%n", SHOW_INVENTORY.getCode(), COMMAND_MAP.get(SHOW_INVENTORY).getName());
        System.out.printf("%d. %s%n", EXIT.getCode(), COMMAND_MAP.get(EXIT).getName());

        System.out.println("----------------------");
    }

    private static void registerCommands() {
        COMMAND_MAP.put(ADD_ITEM, new AddItemCommand());
        COMMAND_MAP.put(CHANGE_ITEM_COUNT, new ChangeItemCountCommand());
        COMMAND_MAP.put(REMOVE_ITEM, new RemoveItemCommand());
        COMMAND_MAP.put(SEARCH_ITEM, new SearchItemCommand());
        COMMAND_MAP.put(SHOW_INVENTORY, new ShowInventoryItemCommand());
        COMMAND_MAP.put(EXIT, new ExitCommand());
    }
}
