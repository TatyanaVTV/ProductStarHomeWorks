package ru.vtv.hw.practical.adventurer.commands;

import ru.vtv.hw.practical.adventurer.AdventurerInventory;
import ru.vtv.hw.practical.adventurer.AdventurerInventoryException;

public class ExitCommand implements InventoryCommand {
    @Override
    public String getName() {
        return "Выход";
    }

    @Override
    public boolean execute(AdventurerInventory inventory, InputProvider inputProvider) throws AdventurerInventoryException {
        return true;
    }
}
