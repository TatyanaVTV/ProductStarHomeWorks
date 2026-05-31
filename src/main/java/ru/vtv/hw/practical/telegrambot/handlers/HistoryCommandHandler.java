package ru.vtv.hw.practical.telegrambot.handlers;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vtv.hw.practical.telegrambot.service.CreditService;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
public class HistoryCommandHandler implements CommandHandler {
    private final CreditService creditService;

    public HistoryCommandHandler(CreditService creditService) {
        this.creditService = creditService;
    }

    @Override
    public List<SendMessage> handle(Update update) {
        var userId = update.getMessage().getFrom().getId().toString();
        var history = creditService.getUserHistory(userId);
        log.debug("Обработка: {} - {} - {}", userId, history.size(), history);

        if (history.isEmpty()) {
            var msg = SendMessage.builder()
                    .chatId(update.getMessage().getChatId())
                    .text("🙁 У вас нет истории запросов.")
                    .build();
            return List.of(msg);
        }

        var response = new StringBuilder("🕒 Ваша история запросов:\n");
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        for (int i = 0; i < Math.min(history.size(), 5); i++) { // показываем последние 5 запросов
            var request = history.get(i);
            response.append(String.format(
                    "%s: %.2f руб., %d мес., %.2f%%, %s\n",
                    request.timestamp().format(formatter),
                    request.amount().doubleValue(),
                    request.termMonths(),
                    request.annualRate().doubleValue(),
                    request.paymentType()
            ));
        }

        var msg = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(response.toString())
                .build();
        return List.of(msg);
    }
}