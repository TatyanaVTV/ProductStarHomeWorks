package ru.vtv.hw.practical.telegrambot.repository;

import ru.vtv.hw.practical.telegrambot.domain.CreditRequest;
import ru.vtv.hw.practical.telegrambot.domain.PaymentType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface RequestRepository {
    void addRequest(CreditRequest request);
    List<CreditRequest> getUserHistory(String userId);
    Map<PaymentType, Long> getPaymentTypeStats();
    List<CreditRequest> findByAmountRange(BigDecimal min, BigDecimal max);
    BigDecimal getTotalAmount();
}
