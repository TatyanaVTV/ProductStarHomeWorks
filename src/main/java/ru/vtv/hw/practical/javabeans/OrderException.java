package ru.vtv.hw.practical.javabeans;

import static java.lang.String.format;

public class OrderException extends IllegalArgumentException {
    private OrderException(String message) {
        super(message);
    }

    public static OrderException itemsNotFromMenu() {
        return new OrderException("Order contains items not existing in menu!");
    }

    public static OrderException invalidCountForOrder(int count) {
        return new OrderException(format("Invalid count for order: %d!", count));
    }
}
