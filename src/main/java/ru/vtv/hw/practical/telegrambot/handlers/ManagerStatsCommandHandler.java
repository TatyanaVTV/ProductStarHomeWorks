package ru.vtv.hw.practical.telegrambot.handlers;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vtv.hw.practical.telegrambot.domain.CommandType;
import ru.vtv.hw.practical.telegrambot.domain.CreditRequest;
import ru.vtv.hw.practical.telegrambot.service.AuthService;
import ru.vtv.hw.practical.telegrambot.service.CreditService;

import java.math.BigDecimal;
import java.util.List;

import static ru.vtv.hw.practical.telegrambot.domain.CommandType.*;
import static ru.vtv.hw.practical.telegrambot.domain.PaymentType.ANNUITY;
import static ru.vtv.hw.practical.telegrambot.domain.PaymentType.DIFFERENTIATED;

@Slf4j
public class ManagerStatsCommandHandler implements CommandHandler {
    private final CreditService creditService;
    private final AuthService authService;

    public ManagerStatsCommandHandler(CreditService creditService, AuthService authService) {
        this.creditService = creditService;
        this.authService = authService;
    }

    @Override
    public List<SendMessage> handle(Update update) {
        var chatId = update.getMessage().getChatId().toString();
        var fullCommand = update.getMessage().getText().trim();

        if (!authService.isAuthorized(chatId)) {
            var msg = SendMessage.builder()
                    .chatId(chatId)
                    .text("⛔ Доступ к статистике запрещён. Используйте /admin для авторизации.")
                    .build();
            return List.of(msg);
        }

        return handleStatsCommand(chatId, fullCommand);
    }

    private CommandType getCommandType(String fullCommand) {
        if (fullCommand.equals(STATS.getText())) return STATS;
        if (fullCommand.equals(STATS_TYPES.getText())) return STATS_TYPES;
        if (fullCommand.startsWith(STATS_RANGE.getText())) return STATS_RANGE;
        if (fullCommand.equals(STATS_TOTAL.getText())) return CommandType.STATS_TOTAL;
        return STATS;
    }

    private List<SendMessage> handleStatsCommand(String chatId, String fullCommand) {
        var command = getCommandType(fullCommand);

        var responseText = switch (command) {
            case STATS -> getTotalStats();
            case STATS_TYPES -> getPaymentTypeStats();
            case STATS_RANGE -> getRangeStats(fullCommand);
            case STATS_TOTAL -> getTotalAmount();
            default -> "Неизвестная статистическая команда.";
        };
        var msg = SendMessage.builder().chatId(chatId).text(responseText).build();
        return List.of(msg);
    }

    private String getTotalStats() {
        var typeStats = creditService.getPaymentTypeStats();
        var totalAmount = creditService.getTotalAmount();
        var totalRequests = typeStats.values().stream().mapToLong(Long::longValue).sum();
        return String.format("📊 Общая статистика:\nВсего расчётов: %d\nАннуитетных: %d\nДифференцированных: %d\nОбщая сумма кредитов: %,.2f руб.",
                totalRequests,
                typeStats.getOrDefault(ANNUITY, 0L),
                typeStats.getOrDefault(DIFFERENTIATED, 0L),
                totalAmount);
    }

    private String getPaymentTypeStats() {
        var stats = creditService.getPaymentTypeStats();
        return String.format("📈 Статистика по типам платежей:\nАннуитетный: %d\nДифференцированный: %d",
                stats.getOrDefault(ANNUITY, 0L),
                stats.getOrDefault(DIFFERENTIATED, 0L));
    }

    private String getRangeStats(String fullCommand) {
        try {
            // Удаляем префикс "/range" и разбиваем аргументы
            var argsPart = fullCommand.substring(STATS_RANGE.getText().length()).trim();
            var parts = argsPart.split("\\s+");
            if (parts.length < 2) {
                return "❌ Неверный формат. Используйте: /range <min> <max>\nПример: /range 10000 500000";
            }
            var min = new BigDecimal(parts[0].replace(',', '.'));
            var max = new BigDecimal(parts[1].replace(',', '.'));

            if (min.compareTo(BigDecimal.ZERO) < 0 || max.compareTo(min) < 0) {
                return "❌ Ошибка: минимальная сумма должна быть >= 0, и максимальная не меньше минимальной.";
            }

            var requests = creditService.findByAmountRange(min, max);
            var sb = new StringBuilder(String.format("🔍 Запросы с суммой от %,.2f до %,.2f руб.:\n", min, max));
            if (requests.isEmpty()) {
                sb.append("Нет запросов в этом диапазоне.");
            } else {
                for (CreditRequest req : requests) {
                    sb.append(String.format("💰 %,.2f руб., %d мес., %s\n", req.amount(), req.termMonths(), req.paymentType()));
                }
            }
            return sb.toString();
        } catch (NumberFormatException e) {
            return "❌ Ошибка: аргументы должны быть числами. Пример: /range 10000 500000";
        }
    }

    private String getTotalAmount() {
        var total = creditService.getTotalAmount();
        return String.format("💰 Общая сумма всех кредитов во всех запросах: %,.2f руб.", total);
    }
}