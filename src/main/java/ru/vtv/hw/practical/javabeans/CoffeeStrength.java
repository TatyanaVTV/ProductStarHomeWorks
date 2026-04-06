package ru.vtv.hw.practical.javabeans;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CoffeeStrength {
    LIGHT(50),
    MEDIUM(100),
    STRONG(150),
    VERY_STRONG(200);

    private final int caffeineLevel;
}
