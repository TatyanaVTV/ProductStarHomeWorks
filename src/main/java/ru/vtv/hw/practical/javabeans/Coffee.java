package ru.vtv.hw.practical.javabeans;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Coffee extends MenuItem implements Preparable {
    private final CoffeeStrength strength;

    @Override
    public void prepare() {
        System.out.printf("%s coffee has been prepared, caffeine level = %d.%n",
                strength.name(), strength.getCaffeineLevel());
    }
}
