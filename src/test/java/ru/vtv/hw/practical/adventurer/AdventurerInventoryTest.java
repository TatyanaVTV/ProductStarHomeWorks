package ru.vtv.hw.practical.adventurer;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.*;

public class AdventurerInventoryTest {

    private static final String HEALTH_POTION = "Зелье здоровья";
    private static final String SWORD = "Меч";
    private static final String SHIELD = "Щит";

    private static final String INVALID_NAME_NULL = "Invalid name for item: 'null'!";
    private static final String INVALID_NAME_BLANK = "Invalid name for item: '   '!";

    @Test
    void testAddItemSuccess() {
        var inventory = new AdventurerInventory();
        inventory.addItem(HEALTH_POTION, 1);
        assertEquals(1, inventory.searchItem(HEALTH_POTION));
    }

    @Test
    void testAddDuplicateItemThrowsException() {
        var inventory = new AdventurerInventory();
        inventory.addItem(SWORD, 3);

        var expectedMsg = "Inventory already has item '" + SWORD + "'!";
        var exception = assertThrows(AdventurerInventoryException.class, () -> inventory.addItem(SWORD, 2));
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    void testAddNullItemThrowsException() {
        var inventory = new AdventurerInventory();
        var exception = assertThrows(AdventurerInventoryException.class,
                () -> inventory.addItem(null, 1));
        assertEquals(INVALID_NAME_NULL, exception.getMessage());
    }

    @Test
    void testAddBlankItemThrowsException() {
        var inventory = new AdventurerInventory();
        var exception = assertThrows(AdventurerInventoryException.class,
                () -> inventory.addItem("   ", 1));
        assertEquals(INVALID_NAME_BLANK, exception.getMessage());
    }

    @Test
    void testRemoveItemSuccess() {
        var inventory = new AdventurerInventory();
        inventory.addItem(SHIELD, 2);
        inventory.removeItem(SHIELD);
        assertNull(inventory.searchItem(SHIELD));
    }

    @Test
    void testRemoveNullItemThrowsException() {
        var inventory = new AdventurerInventory();
        var exception = assertThrows(AdventurerInventoryException.class, () -> inventory.removeItem(null));
        assertEquals(INVALID_NAME_NULL, exception.getMessage());
    }

    @Test
    void testRemoveBlankItemThrowsException() {
        var inventory = new AdventurerInventory();
        var exception = assertThrows(AdventurerInventoryException.class, () -> inventory.removeItem("   "));
        assertEquals(INVALID_NAME_BLANK, exception.getMessage());
    }

    @Test
    void testChangeItemCountSuccess() {
        var inventory = new AdventurerInventory();
        inventory.addItem(SWORD, 3);
        inventory.changeItemCount(SWORD, 5);
        assertEquals(5, inventory.searchItem(SWORD));
    }

    @Test
    void testChangeItemCountToZeroRemovesItem() {
        var inventory = new AdventurerInventory();
        inventory.addItem(HEALTH_POTION, 1);
        inventory.changeItemCount(HEALTH_POTION, 0);
        assertNull(inventory.searchItem(HEALTH_POTION));
    }

    @Test
    void testChangeItemNegativeCountThrowsException() {
        var inventory = new AdventurerInventory();
        inventory.addItem(SHIELD, 2);
        var expectedMsg = "Wrong count for item 'Щит', count = -1'!";
        var exception = assertThrows(AdventurerInventoryException.class,
                () -> inventory.changeItemCount(SHIELD, -1));
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    void testSearchItemFound() {
        var inventory = new AdventurerInventory();
        inventory.addItem(SWORD, 3);
        assertEquals(3, inventory.searchItem(SWORD));
    }

    @Test
    void testSearchItemNotFound() {
        var inventory = new AdventurerInventory();
        assertNull(inventory.searchItem(HEALTH_POTION));
    }

    @Test
    void testSearchNullItemThrowsException() {
        var inventory = new AdventurerInventory();
        var exception = assertThrows(AdventurerInventoryException.class, () -> inventory.searchItem(null));
        assertEquals(INVALID_NAME_NULL, exception.getMessage());
    }

    @Test
    void testSearchBlankItemThrowsException() {
        var inventory = new AdventurerInventory();
        var exception = assertThrows(AdventurerInventoryException.class, () -> inventory.searchItem("   "));
        assertEquals(INVALID_NAME_BLANK, exception.getMessage());
    }

    @SneakyThrows
    @Test
    void testPrintInventoryOutput() {
        var inventory = new AdventurerInventory();
        inventory.addItem(SWORD, 3);
        inventory.addItem(SHIELD, 2);

        var output = tapSystemOut(inventory::printInventory);
        assertTrue(output.contains("- Меч - 3"));
        assertTrue(output.contains("- Щит - 2"));
    }

    @Test
    void testInitialInventoryEmpty() {
        var inventory = new AdventurerInventory();
        assertTrue(inventory.isEmpty());
    }
}
