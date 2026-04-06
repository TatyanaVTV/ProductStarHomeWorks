package ru.vtv.hw.practical.javabeans;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@SuperBuilder
@Getter
public abstract class MenuItem {
    private final String name;
    private final BigDecimal price;
}
