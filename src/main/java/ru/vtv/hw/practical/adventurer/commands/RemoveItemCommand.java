package ru.vtv.hw.practical.adventurer.commands;

import ru.vtv.hw.practical.adventurer.AdventurerInventory;
import ru.vtv.hw.practical.adventurer.AdventurerInventoryException;

import static java.util.Objects.isNull;

public class RemoveItemCommand extends BaseInventoryCommand implements InventoryCommand {
    @Override
    public String getName() {
        return "Удалить предмет";
    }

    @Override
    public boolean execute(AdventurerInventory inventory, InputProvider inputProvider) throws AdventurerInventoryException {
        var itemName = requestItemName(inputProvider);

        if (!isNull(inventory.searchItem(itemName))) {
            inventory.removeItem(itemName);
            System.out.printf("Предмет '%s' успешно удалён.%n", itemName);
        } else {
            System.out.printf("Предмет '%s' не найден в инвентаре.%n", itemName);
        }
        return true;
    }
}
