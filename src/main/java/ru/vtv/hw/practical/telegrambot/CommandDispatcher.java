package ru.vtv.hw.practical.telegrambot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vtv.hw.practical.telegrambot.handlers.*;
import ru.vtv.hw.practical.telegrambot.domain.CommandType;
import ru.vtv.hw.practical.telegrambot.service.AuthService;
import ru.vtv.hw.practical.telegrambot.service.CreditService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;
import static ru.vtv.hw.practical.telegrambot.domain.CommandType.*;

@Slf4j
public class CommandDispatcher {
    private final Map<CommandType, CommandHandler> handlers = new HashMap<>();
    private final CreditService creditService;
    private final AuthService authService;

    private final Map<String, CommandHandler> activeDialogs = new ConcurrentHashMap<>();

    public CommandDispatcher(CreditService creditService, AuthService authService) {
        this.creditService = creditService;
        this.authService = authService;
        initHandlers();
    }

    private void initHandlers() {
        handlers.put(START, new StartCommandHandler(authService));
        handlers.put(CALCULATE, new CalculateCommandHandler(creditService, this));
        handlers.put(HISTORY, new HistoryCommandHandler(creditService));
        handlers.put(ADMIN, new AdminCommandHandler(authService, this));
        // Общий обработчик для всех статистических команд
        handlers.put(STATS, new ManagerStatsCommandHandler(creditService, authService));
        handlers.put(STATS_TYPES, new ManagerStatsCommandHandler(creditService, authService));
        handlers.put(STATS_RANGE, new ManagerStatsCommandHandler(creditService, authService));
        handlers.put(STATS_TOTAL, new ManagerStatsCommandHandler(creditService, authService));
    }

    public List<SendMessage> dispatch(Update update) {
        var chatId = update.getMessage().getChatId().toString();
        var message = update.getMessage().getText().trim();
        var command = parseCommand(message);

        // Если для этого чата активен диалог – перенаправляем в хендлер диалога
        if (activeDialogs.containsKey(chatId)) {
            var dialogHandler = activeDialogs.get(chatId);
            return dialogHandler.handle(update);
        }

        log.debug("Processing command: {}, message: {}, chatId: {}", command, message, chatId);
        if (!isNull(command) && handlers.containsKey(command)) {
            return handlers.get(command).handle(update);
        }

        var msg = SendMessage.builder()
                .chatId(chatId)
                .text("Неизвестная команда. Используйте /start, /calculate, /history, /stats, /types, /range <min> <max>, /total")
                .build();
        return List.of(msg);
    }

    private CommandType parseCommand(String text) {
        for (CommandType cmd : CommandType.values()) {
            if (cmd.getText().equalsIgnoreCase(text)) {
                return cmd;
            }
        }

        if (text.toLowerCase().startsWith(STATS_RANGE.getText() + " ")) {
            return STATS_RANGE;
        }
        return null;
    }

    public void setActiveDialog(String chatId, CommandHandler handler) {
        activeDialogs.put(chatId, handler);
    }

    public void removeActiveDialog(String chatId) {
        activeDialogs.remove(chatId);
    }
}
