package ru.vtv.hw.practical.javabeans;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Pastry extends MenuItem {
    private final PastryFeature feature;
}
