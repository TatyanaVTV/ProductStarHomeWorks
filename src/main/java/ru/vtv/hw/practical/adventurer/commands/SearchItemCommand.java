package ru.vtv.hw.practical.adventurer.commands;

import ru.vtv.hw.practical.adventurer.AdventurerInventory;
import ru.vtv.hw.practical.adventurer.AdventurerInventoryException;

import static java.util.Objects.isNull;

public class SearchItemCommand extends BaseInventoryCommand implements InventoryCommand {
    @Override
    public String getName() {
        return "Найти предмет по названию";
    }

    @Override
    public boolean execute(AdventurerInventory inventory, InputProvider inputProvider) throws AdventurerInventoryException {
        var itemName = requestItemName(inputProvider);

        var foundItem = inventory.searchItem(itemName);
        if (isNull(foundItem)) {
            System.out.printf("Предмет '%s' не найден.%n", itemName);
        } else {
            System.out.printf("Количество \"%s\": %d.%n", itemName, foundItem);
        }
        return true;
    }
}
