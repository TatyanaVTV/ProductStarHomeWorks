package ru.vtv.hw.practical.telegrambot.calculator;

import lombok.experimental.UtilityClass;
import ru.vtv.hw.practical.telegrambot.domain.PaymentType;

@UtilityClass
public class PaymentScheduleCalculatorFactory {

    public static PaymentScheduleCalculator create(PaymentType paymentType) {
        return switch (paymentType) {
            case ANNUITY -> new AnnuityScheduleCalculator();
            case DIFFERENTIATED ->  new DifferentiatedScheduleCalculator();
        };
    }
}
