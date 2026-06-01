package ru.vtv.hw.practical.telegrambot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.lang.String.format;

@RequiredArgsConstructor
@Getter
public enum PaymentType {
    ANNUITY(1),
    DIFFERENTIATED(2);

    private final int order;

    @Override
    public String toString() {
        return format("%d - %s", order, name());
    }
}
