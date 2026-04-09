package ru.vtv.hw.practical.adventurer.commands;

import ru.vtv.hw.practical.adventurer.AdventurerInventory;
import ru.vtv.hw.practical.adventurer.AdventurerInventoryException;

import static java.util.Objects.isNull;

public class AddItemCommand extends BaseInventoryCommand implements InventoryCommand {
    @Override
    public String getName() {
        return "Добавить новый предмет";
    }

    @Override
    public boolean execute(AdventurerInventory inventory, InputProvider inputProvider) throws AdventurerInventoryException {
        var itemName = requestItemName(inputProvider);
        int quantity = requestItemQuantity(inputProvider, 1);

        if (!isNull(inventory.searchItem(itemName))) {
            boolean shouldUpdate = inputProvider.getConfirmation(
                    "Такой предмет уже есть в инвентаре. Обновить количество?");
            if (!shouldUpdate) {
                System.out.println(EXECUTION_CANCELED);
                return false;
            }

            System.out.printf("Новое количество = %d.%n", quantity);
            if (quantity == 0) {
                inventory.removeItem(itemName);
                System.out.printf("Предмет '%s' успешно удалён.%n", itemName);
                return true;
            } else {
                inventory.changeItemCount(itemName, quantity);
                System.out.printf("Количество для '%s' обновлено (%d).%n", itemName, quantity);
                return true;
            }
        }

        inventory.addItem(itemName, quantity);
        System.out.printf("Предмет '%s' успешно добавлен в количестве %d.%n", itemName, quantity);
        return true;
    }
}
