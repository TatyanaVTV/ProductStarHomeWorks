package ru.vtv.hw.practical.javabeans;

import static java.lang.String.format;

public class MenuItemException extends IllegalArgumentException {
    private MenuItemException(String message) {
        super(message);
    }

    public static MenuItemException noItemToAdd() {
        return new MenuItemException("Impossible to add empty MenuItem!");
    }

    public static MenuItemException itemNotFound() {
        return new MenuItemException("MenuItem not found!");
    }

    public static MenuItemException emptyName() {
        return new MenuItemException("Impossible to process MenuItem without name!");
    }

    public static MenuItemException priceIsNotValid(MenuItem menuItem) {
        return new MenuItemException(
                format("Invalid price for menuItem: %s, price: %.2f",
                        menuItem.getName(), menuItem.getPrice()));
    }
}
