package ru.vtv.hw.practical.telegrambot.calculator;

import ru.vtv.hw.practical.telegrambot.domain.Payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

public class AnnuityScheduleCalculator implements PaymentScheduleCalculator {

    @Override
    public List<Payment> calculate(BigDecimal amount, int termMonths, BigDecimal annualRate) {
        var monthlyRate = calculateMonthlyRate(annualRate);
        var monthlyPayment = calculateMonthlyPayment(amount, termMonths, monthlyRate);

        var payments = new ArrayList<Payment>();
        var remainingBalance = new BigDecimal(amount.toString());

        for (int month = 1; month <= termMonths; month++) {
            var interest = remainingBalance.multiply(monthlyRate).setScale(2, HALF_UP);

            BigDecimal principal;
            BigDecimal payment;

            if (month == termMonths) {
                principal = remainingBalance;
                payment = principal.add(interest);
                remainingBalance = ZERO;
            } else {
                principal = monthlyPayment.subtract(interest).setScale(2, HALF_UP);
                remainingBalance = remainingBalance.subtract(principal).setScale(2, HALF_UP);
                payment = monthlyPayment;
            }

            payments.add(new Payment(
                    month,
                    payment,
                    principal,
                    interest,
                    remainingBalance.compareTo(ZERO) > 0 ? remainingBalance : ZERO
            ));
        }
        return payments;
    }

    private BigDecimal calculateMonthlyRate(BigDecimal annualRate) {
        return annualRate
                .divide(BigDecimal.valueOf(12), 10, HALF_UP)
                .divide(BigDecimal.valueOf(100), 10, HALF_UP);
    }

    /**
     * Формула аннуитетного платежа: A = K * S, где K — коэффициент аннуитета
     * K = (i * (1 + i)^n) / ((1 + i)^n - 1)
     * */
    private BigDecimal calculateMonthlyPayment(BigDecimal amount, int termMonths, BigDecimal monthlyRate) {
        var base = ONE.add(monthlyRate);
        var pow = base.pow(termMonths).setScale(10, HALF_UP);

        var numerator = monthlyRate.multiply(pow);
        var denominator = pow.subtract(ONE);
        var annuityCoefficient = numerator.divide(denominator, 10, HALF_UP);

        return amount.multiply(annuityCoefficient).setScale(2, HALF_UP);
    }
}
