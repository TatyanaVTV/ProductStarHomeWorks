package ru.vtv.hw.practical.adventurer.commands;

import ru.vtv.hw.practical.adventurer.AdventurerInventory;
import ru.vtv.hw.practical.adventurer.AdventurerInventoryException;

public interface InventoryCommand {
    String getName();
    boolean execute(AdventurerInventory inventory, InputProvider inputProvider) throws AdventurerInventoryException;
}
