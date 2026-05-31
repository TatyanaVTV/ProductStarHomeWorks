package ru.vtv.hw.practical.telegrambot.service;

import ru.vtv.hw.practical.telegrambot.domain.CreditRequest;
import ru.vtv.hw.practical.telegrambot.domain.Payment;
import ru.vtv.hw.practical.telegrambot.domain.PaymentType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CreditService {
    List<Payment> calculateSchedule(BigDecimal amount, int termMonths, BigDecimal annualRate, PaymentType type);
    void saveRequest(String userId, BigDecimal amount, int termMonths,
                     BigDecimal annualRate, PaymentType type, List<Payment> schedule);
    List<CreditRequest> getUserHistory(String userId);
    Map<PaymentType, Long> getPaymentTypeStats();
    List<CreditRequest> findByAmountRange(BigDecimal min, BigDecimal max);
    BigDecimal getTotalAmount();
}
