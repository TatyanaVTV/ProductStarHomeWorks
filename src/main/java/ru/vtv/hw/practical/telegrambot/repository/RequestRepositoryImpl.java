package ru.vtv.hw.practical.telegrambot.repository;

import ru.vtv.hw.practical.telegrambot.domain.CreditRequest;
import ru.vtv.hw.practical.telegrambot.domain.PaymentType;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class RequestRepositoryImpl implements RequestRepository {
    private final Map<String, List<CreditRequest>> userRequests = new HashMap<>();

    @Override
    public void addRequest(CreditRequest request) {
        userRequests.computeIfAbsent(request.userId(), k -> new ArrayList<>()).add(request);
    }

    @Override
    public List<CreditRequest> getUserHistory(String userId) {
        return userRequests.getOrDefault(userId, Collections.emptyList());
    }

    @Override
    // Статистика по типам платежей
    public Map<PaymentType, Long> getPaymentTypeStats() {
        return userRequests.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(CreditRequest::paymentType, Collectors.counting()));
    }

    @Override
    // Поиск запросов в диапазоне сумм
    public List<CreditRequest> findByAmountRange(BigDecimal min, BigDecimal max) {
        return userRequests.values().stream()
                .flatMap(List::stream)
                .filter(r -> r.amount().compareTo(min) >= 0 && r.amount().compareTo(max) <= 0)
                .toList();
    }

    @Override
    // Общая сумма всех запросов
    public BigDecimal getTotalAmount() {
        return userRequests.values().stream()
                .flatMap(List::stream)
                .map(CreditRequest::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
