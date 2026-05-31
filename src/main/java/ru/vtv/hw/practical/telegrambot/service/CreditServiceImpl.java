package ru.vtv.hw.practical.telegrambot.service;

import ru.vtv.hw.practical.telegrambot.calculator.PaymentScheduleCalculatorFactory;
import ru.vtv.hw.practical.telegrambot.domain.CreditRequest;
import ru.vtv.hw.practical.telegrambot.domain.Payment;
import ru.vtv.hw.practical.telegrambot.domain.PaymentType;
import ru.vtv.hw.practical.telegrambot.repository.RequestRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class CreditServiceImpl implements CreditService {
    private final RequestRepository repository;

    public CreditServiceImpl(RequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Payment> calculateSchedule(BigDecimal amount, int termMonths, BigDecimal annualRate, PaymentType type) {
        var calculator = PaymentScheduleCalculatorFactory.create(type);
        return calculator.calculate(amount, termMonths, annualRate);
    }

    @Override
    public void saveRequest(String userId, BigDecimal amount, int termMonths,
                            BigDecimal annualRate, PaymentType type, List<Payment> schedule) {
        var request = new CreditRequest(userId, LocalDateTime.now(),
                amount, termMonths, annualRate, type, schedule);
        repository.addRequest(request);
    }

    @Override
    public List<CreditRequest> getUserHistory(String userId) {
        return repository.getUserHistory(userId);
    }

    @Override
    public Map<PaymentType, Long> getPaymentTypeStats() {
        return repository.getPaymentTypeStats();
    }

    @Override
    public List<CreditRequest> findByAmountRange(BigDecimal min, BigDecimal max) {
        return repository.findByAmountRange(min, max);
    }

    @Override
    public BigDecimal getTotalAmount() {
        return repository.getTotalAmount();
    }
}
