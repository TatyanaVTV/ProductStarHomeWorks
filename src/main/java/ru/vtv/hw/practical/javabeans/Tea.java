package ru.vtv.hw.practical.javabeans;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Tea extends MenuItem implements Preparable {
    private final TeaType type;

    @Override
    public void prepare() {
        System.out.printf("%s tea has been prepared.%n", this.type.name());
    }
}
