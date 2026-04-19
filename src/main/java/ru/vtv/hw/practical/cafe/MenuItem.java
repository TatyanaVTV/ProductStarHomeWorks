package ru.vtv.hw.practical.cafe;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MenuItem {
    SOUP("Суп", 5, false),
    SALAD("Салат", 3, false),
    BURGER("Бургер", 4, false),
    COFFEE("Кофе", 2, true),
    TEA("Чай", 2, true),
    SPARKLING_WATER("Газированная вода", 1, true);

    private final String rusName;
    private final int secondsToCook;
    private final boolean isDrink;

    public static MenuItem getRandomMenuItem() {
        return MenuItem.values()[(int) (Math.random() * MenuItem.values().length)];
    }
}
