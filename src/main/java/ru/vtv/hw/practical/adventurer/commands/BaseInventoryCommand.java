package ru.vtv.hw.practical.adventurer.commands;

import static java.util.Objects.isNull;

public abstract class BaseInventoryCommand {
    protected final String EXECUTION_CANCELED = "Операция отменена";

    protected final String requestItemName(InputProvider inputProvider) {
        String itemName = null;
        while (isNull(itemName) || itemName.isBlank()) {
            itemName = inputProvider.getInput("Введите название предмета");
            if (itemName.isBlank()) {
                System.out.println("Название предмета не может быть пустым!");
            }
        }
        return itemName;
    }

    protected final int requestItemQuantity(InputProvider inputProvider, int minQuantity) {
        Integer quantity = null;
        while (isNull(quantity) || quantity < minQuantity) {
            var quantityStr = inputProvider.getInput("Введите количество");
            try {
                var qty = Integer.parseInt(quantityStr);
                if (qty < 0) {
                    System.out.println("Количество не может быть отрицательным числом!");
                }
                if (qty < minQuantity) {
                    System.out.println("Некорректное значение. Минимальное значение: " + minQuantity);
                }
                quantity = qty;
            } catch (NumberFormatException e) {
                System.out.println("Некорректное количество. Введите число!");
            }
        }
        return quantity;
    }
}
