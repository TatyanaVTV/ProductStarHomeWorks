package ru.vtv.hw.practical.telegrambot.domain;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public record Payment(
        int month,
        BigDecimal totalPayment,
        BigDecimal principal,
        BigDecimal interest,
        BigDecimal remainingBalance
) {
    private static final String TO_STRING_FORMAT = "№ %d: %.2f руб. (ОД: %.2f, проценты: %.2f, остаток: %.2f)";

    @Override
    public @NotNull String toString() {
        return String.format(TO_STRING_FORMAT,
                month,
                totalPayment.doubleValue(),
                principal.doubleValue(),
                interest.doubleValue(),
                remainingBalance.doubleValue());
    }
}