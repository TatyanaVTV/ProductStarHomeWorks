package ru.vtv.hw.practical.javabeans;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Size {
    SMALL(0.9),
    MEDIUM(1),
    LARGE(1.2);

    private final double multiplier;
}
