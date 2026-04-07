package ru.vtv.hw.practical.adventurer;

import static java.lang.String.format;

public class AdventurerInventoryException extends RuntimeException {
    private AdventurerInventoryException(String message) {
        super(message);
    }

    public static AdventurerInventoryException invalidName(String item) {
        return new AdventurerInventoryException("Invalid name for item: '" + item + "'!");
    }

    public static AdventurerInventoryException alreadyExists(String item) {
        return new AdventurerInventoryException("Inventory already has item '" + item + "'!");
    }

    public static AdventurerInventoryException wrongCount(String item, int count) {
        return new AdventurerInventoryException(format("Wrong count for item '%s', count = %d'!", item, count));
    }
}
