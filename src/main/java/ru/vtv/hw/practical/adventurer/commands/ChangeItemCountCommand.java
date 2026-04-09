package ru.vtv.hw.practical.adventurer.commands;

import ru.vtv.hw.practical.adventurer.AdventurerInventory;
import ru.vtv.hw.practical.adventurer.AdventurerInventoryException;

import static java.lang.String.format;
import static java.util.Objects.isNull;

public class ChangeItemCountCommand extends BaseInventoryCommand implements InventoryCommand {
    @Override
    public String getName() {
        return "Изменить количество предметов";
    }

    @Override
    public boolean execute(AdventurerInventory inventory, InputProvider inputProvider) throws AdventurerInventoryException {
        var itemName = requestItemName(inputProvider);
        int quantity = requestItemQuantity(inputProvider, 0);

        if (isNull(inventory.searchItem(itemName))) {
            boolean shouldUpdate = inputProvider.getConfirmation(
                    "Такого предмета не существует. Добавить его в инвентарь?");
            if (!shouldUpdate) {
                System.out.println(EXECUTION_CANCELED);
                return false;
            }
            inventory.addItem(itemName, quantity);
            System.out.printf("Предмет '%s' добавлен в количестве %d%n", itemName, quantity);
            return true;
        }

        inventory.changeItemCount(itemName, quantity);

        var isItemRemoved = isNull(inventory.searchItem(itemName));
        var msg = isItemRemoved
                ? format("Предмет '%s' был успешно удалён.%n", itemName)
                : format("Количество для '%s' обновлено (%d).%n", itemName, quantity);
        System.out.print(msg);
        return true;
    }
}
