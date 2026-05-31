package ru.vtv.hw.practical.telegrambot.handlers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vtv.hw.practical.telegrambot.CommandDispatcher;
import ru.vtv.hw.practical.telegrambot.domain.Payment;
import ru.vtv.hw.practical.telegrambot.domain.PaymentType;
import ru.vtv.hw.practical.telegrambot.service.CreditService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import static java.util.Objects.isNull;
import static ru.vtv.hw.practical.telegrambot.domain.CommandType.*;

@Slf4j
public class CalculateCommandHandler implements CommandHandler {
    private final CreditService creditService;
    private final CommandDispatcher dispatcher;

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+(\\.\\d+)?$");
    private static final int PAYMENTS_PER_MESSAGE = 24;
    private final Map<String, DialogState> userStates = new ConcurrentHashMap<>();

    public CalculateCommandHandler(CreditService creditService, CommandDispatcher commandDispatcher) {
        this.creditService = creditService;
        this.dispatcher = commandDispatcher;
    }

    @Override
    public List<SendMessage> handle(Update update) {
        var message = update.getMessage();
        var chatId = message.getChatId().toString();
        var text = message.getText().trim();

        log.debug("Обработка: chatId={}, text={}", chatId, text);

        if (isCalculateCommand(text)) {
            return List.of(startDialog(chatId));
        }

        var state = userStates.get(chatId);
        if (isNull(state)) {
            return List.of(sendMessage(chatId, "Начните заново с " + CALCULATE));
        }

        try {
            return switch (state.getStep()) {
                case AWAITING_AMOUNT -> handleAmount(chatId, text, state);
                case AWAITING_TERM -> handleTerm(chatId, text, state);
                case AWAITING_RATE -> handleRate(chatId, text, state);
                case AWAITING_PAYMENT_TYPE -> handlePaymentType(chatId, text, state);
            };
        } catch (Exception e) {
            log.error("Ошибка при расчёте", e);
            finishDialog(chatId);
            var msg = sendMessage(chatId,  format("Произошла ошибка: %s. Начните заново с %s", e.getMessage(), CALCULATE));
            return List.of(msg);
        }
    }

    private boolean isCalculateCommand(String text) {
        return CALCULATE.getText().equals(text);
    }

    private SendMessage startDialog(String chatId) {
        userStates.put(chatId, new DialogState());
        dispatcher.setActiveDialog(chatId, this);
        return sendMessage(chatId, "Введите сумму кредита (например, 100000):");
    }

    private List<SendMessage> handleAmount(String chatId, String text, DialogState state) {
        if (!isValidNumber(text)) {
            var msg = sendMessage(chatId, "Пожалуйста, введите корректное число для суммы:");
            return List.of(msg);
        }
        var amount = new BigDecimal(text).setScale(2, HALF_UP);
        state.setAmount(amount);
        state.setStep(Step.AWAITING_TERM);
        var msg = sendMessage(chatId, "Введите срок кредита в месяцах (например, 60):");
        return List.of(msg);
    }

    private List<SendMessage> handleTerm(String chatId, String text, DialogState state) {
        if (!isValidNumber(text) || new BigDecimal(text).scale() > 0) {
            var msg = sendMessage(chatId, "Пожалуйста, введите целое число для срока:");
            return List.of(msg);
        }
        var term = Integer.parseInt(text);
        state.setTermMonths(term);
        state.setStep(Step.AWAITING_RATE);
        var msg = sendMessage(chatId, "Введите годовую процентную ставку (например, 10.5):");
        return List.of(msg);
    }

    private List<SendMessage> handleRate(String chatId, String text, DialogState state) {
        if (!isValidNumber(text)) {
            var msg = sendMessage(chatId, "Пожалуйста, введите корректное число для ставки:");
            return List.of(msg);
        }
        var rate = new BigDecimal(text).setScale(2, HALF_UP);
        state.setAnnualRate(rate);
        state.setStep(Step.AWAITING_PAYMENT_TYPE);
        var options = getPaymentTypeOptions();
        var msg = sendMessage(chatId, "Выберите тип платежа: " + options);
        return List.of(msg);
    }

    private List<SendMessage> handlePaymentType(String chatId, String text, DialogState state) {
        var paymentType = parsePaymentType(text);
        if (isNull(paymentType)) {
            var options = getPaymentTypeOptions();
            var msg = sendMessage(chatId, "Неверный тип платежа. Возможные варианты: " + options);
            return List.of(msg);
        }
        state.setPaymentType(paymentType);

        var schedule = creditService.calculateSchedule(
                state.getAmount(), state.getTermMonths(), state.getAnnualRate(), paymentType);
        creditService.saveRequest(chatId, state.getAmount(), state.getTermMonths(),
                state.getAnnualRate(), paymentType, schedule);

        var menuHint = """
                ✅ Расчёт завершён. Выберите действие:
                %s — новый расчёт
                %s — история ваших расчётов
                %s — главное меню
                """.formatted(CALCULATE, HISTORY, START);

        var messages = new ArrayList<>(buildScheduleMessages(chatId, schedule));
        messages.add(sendMessage(chatId, getPaymentSummary(schedule, state)));
        messages.add(sendMessage(chatId, menuHint));

        finishDialog(chatId);
        return messages;
    }


    private String getPaymentTypeOptions() {
        return Arrays.stream(PaymentType.values())
                .map(PaymentType::toString)
                .collect(Collectors.joining(", "));
    }

    private PaymentType parsePaymentType(String input) {
        var trimmed = input.trim().toUpperCase();
        // Попытка распарсить как имя enum
        try {
            return PaymentType.valueOf(trimmed);
        } catch (IllegalArgumentException ignored) {
            // Не имя enum, попробуем число
        }
        // Попытка распарсить как число (order)
        try {
            var order = Integer.parseInt(trimmed);
            for (var type : PaymentType.values()) {
                if (type.getOrder() == order) {
                    return type;
                }
            }
        } catch (NumberFormatException ignored) {
        }
        return null;
    }

    /**
     * Разбивает график платежей на несколько сообщений по PAYMENTS_PER_MESSAGE строк.
     * Первое сообщение содержит заголовок "📊 График платежей:",
     * последующие — "📄 Продолжение графика:".
     */
    private List<SendMessage> buildScheduleMessages(String chatId, List<Payment> schedule) {
        var messages = new ArrayList<SendMessage>();
        if (schedule.isEmpty()) {
            messages.add(sendMessage(chatId, "📊 График платежей:\nНет данных."));
            return messages;
        }

        int total = schedule.size();
        int chunkCount = (total + PAYMENTS_PER_MESSAGE - 1) / PAYMENTS_PER_MESSAGE;
        for (int i = 0; i < chunkCount; i++) {
            var sb = new StringBuilder();
            if (i == 0) {
                sb.append("📊 График платежей:\n");
            } else {
                sb.append("📄 Продолжение графика:\n");
            }

            int start = i * PAYMENTS_PER_MESSAGE;
            int end = Math.min(start + PAYMENTS_PER_MESSAGE, total);
            for (int j = start; j < end; j++) {
                sb.append(schedule.get(j)).append("\n");
            }

            messages.add(sendMessage(chatId, sb.toString()));
        }

        return messages;
    }

    private String getPaymentSummary(List<Payment> schedule, DialogState state) {
        var totalPayments = ZERO;
        var totalInterest = ZERO;
        for (Payment p : schedule) {
            totalPayments = totalPayments.add(p.totalPayment());
            totalInterest = totalInterest.add(p.interest());
        }
        totalPayments = totalPayments.setScale(2, HALF_UP);
        var overpayment = totalInterest.setScale(2, HALF_UP);

        var params = String.format("- %,.2f руб. на %d мес., %s, процентная ставка: %,.2f%%",
                state.amount, state.termMonths, state.paymentType, state.annualRate);
        var totalPaymentsStr = String.format("- Общая сумма платежей: %,.2f руб.", totalPayments);
        var overpaymentStr = String.format("- Общая сумма переплаты (проценты): %,.2f руб.", overpayment);

        return String.format("📈 Итоги по кредиту:\n%s\n%s\n%s", params, totalPaymentsStr, overpaymentStr);
    }

    private void finishDialog(String chatId) {
        userStates.remove(chatId);
        dispatcher.removeActiveDialog(chatId);
    }

    private SendMessage sendMessage(String chatId, String text) {
        return SendMessage.builder().chatId(chatId).text(text).build();
    }

    private boolean isValidNumber(String text) {
        return NUMBER_PATTERN.matcher(text).matches();
    }

    @Data
    private static class DialogState {
        private Step step = Step.AWAITING_AMOUNT;
        private BigDecimal amount;
        private Integer termMonths;
        private BigDecimal annualRate;
        private PaymentType paymentType;
    }

    private enum Step {
        AWAITING_AMOUNT, AWAITING_TERM, AWAITING_RATE, AWAITING_PAYMENT_TYPE
    }
}
