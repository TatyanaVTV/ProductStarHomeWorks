package ru.vtv.hw.practical.adventurer.commands;

import ru.vtv.hw.practical.adventurer.AdventurerInventory;
import ru.vtv.hw.practical.adventurer.AdventurerInventoryException;

public class ShowInventoryItemCommand extends BaseInventoryCommand implements InventoryCommand {
    @Override
    public String getName() {
        return "Показать весь инвентарь";
    }

    @Override
    public boolean execute(AdventurerInventory inventory, InputProvider inputProvider) throws AdventurerInventoryException {
        System.out.println("Текущий инвентарь: ");
        inventory.printInventory();
        return true;
    }
}
