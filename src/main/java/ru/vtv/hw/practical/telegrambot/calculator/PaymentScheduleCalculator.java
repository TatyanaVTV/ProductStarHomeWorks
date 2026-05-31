package ru.vtv.hw.practical.telegrambot.calculator;

import ru.vtv.hw.practical.telegrambot.domain.Payment;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentScheduleCalculator {
    List<Payment> calculate(BigDecimal amount, int termMonths, BigDecimal annualRate);
}
