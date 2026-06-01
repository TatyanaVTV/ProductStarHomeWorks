package ru.vtv.hw.practical.telegrambot.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CreditRequest(
        String userId,
        LocalDateTime timestamp,
        BigDecimal amount,
        int termMonths,
        BigDecimal annualRate,
        PaymentType paymentType,
        List<Payment> schedule
) {}
