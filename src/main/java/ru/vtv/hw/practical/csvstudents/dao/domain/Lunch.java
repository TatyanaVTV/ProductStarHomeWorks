package ru.vtv.hw.practical.csvstudents.dao.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Lunch {
    FREE_OR_REDUCED("free/reduced"),
    STANDARD("standard");

    private final String value;
}
