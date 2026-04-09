package ru.vtv.hw.practical.adventurer;

import java.util.*;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static ru.vtv.hw.practical.adventurer.AdventurerInventoryException.*;

public class AdventurerInventory {
    private final Map<String, Integer> inventory = new LinkedHashMap<>();

    public void addItem(String itemName, int amount) {
        if (inventory.containsKey(itemName)) throw alreadyExists(itemName);
        if (isNull(itemName) || itemName.isBlank()) throw invalidName(itemName);

        inventory.put(itemName, amount);
    }

    public void removeItem(String itemName) {
        if (isNull(itemName) || itemName.isBlank()) throw invalidName(itemName);
        inventory.remove(itemName);
    }

    public void changeItemCount(String itemName, int count) {
        if (isNull(itemName) || itemName.isBlank()) throw invalidName(itemName);
        if (count < 0) throw wrongCount(itemName, count);

        if (count == 0) {
            inventory.remove(itemName);
            return;
        }

        inventory.put(itemName, count);
    }

    public Integer searchItem(String itemName) {
        if (isNull(itemName) || itemName.isBlank()) throw invalidName(itemName);
        return inventory.getOrDefault(itemName, null);
    }

    public void printInventory() {
        inventory.entrySet().stream()
                .map(entry -> format("- %s - %d", entry.getKey(), entry.getValue()))
                .forEach(System.out::println);
    }

    public boolean isEmpty() {
        return inventory.isEmpty();
    }
}
