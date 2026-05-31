package ru.vtv.hw.practical.telegrambot.calculator;

import ru.vtv.hw.practical.telegrambot.domain.Payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

public class DifferentiatedScheduleCalculator implements PaymentScheduleCalculator {

    @Override
    public List<Payment> calculate(BigDecimal amount, int termMonths, BigDecimal annualRate) {
        var basePrincipal = calculateBasePrincipal(amount, termMonths);
        var monthlyRate = calculateMonthlyRate(annualRate);

        var payments = new ArrayList<Payment>();
        var remainingBalance = amount.setScale(2, HALF_UP);

        for (int month = 1; month <= termMonths; month++) {
            var interest = remainingBalance.multiply(monthlyRate).setScale(2, HALF_UP);

            BigDecimal principal;
            BigDecimal totalPayment;

            if (month == termMonths) {
                principal = remainingBalance;
                totalPayment = basePrincipal.add(interest).setScale(2, HALF_UP);
                remainingBalance = ZERO;
            } else {
                principal = basePrincipal;
                totalPayment = principal.add(interest).setScale(2, HALF_UP);
                remainingBalance = remainingBalance.subtract(principal).setScale(2, HALF_UP);
            }

            payments.add(new Payment(
                    month,
                    totalPayment,
                    principal,
                    interest,
                    remainingBalance.compareTo(ZERO) > 0 ? remainingBalance : ZERO
            ));
        }
        return payments;
    }

    /**
     * Фиксированная часть основного долга, которая выплачивается каждый месяц
     * Рассчитывается как общая сумма кредита, делённая на количество месяцев
     * */
    private BigDecimal calculateBasePrincipal(BigDecimal amount, int termMonths) {
        return amount.divide(BigDecimal.valueOf(termMonths), 2, HALF_UP);
    }

    private BigDecimal calculateMonthlyRate(BigDecimal annualRate) {
        return annualRate
                .divide(BigDecimal.valueOf(12), 10, HALF_UP)
                .divide(BigDecimal.valueOf(100), 10, HALF_UP);
    }
}
