package ru.vtv.hw.practical.telegrambot.handlers;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vtv.hw.practical.telegrambot.CommandDispatcher;
import ru.vtv.hw.practical.telegrambot.domain.CommandType;
import ru.vtv.hw.practical.telegrambot.service.AuthService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static ru.vtv.hw.practical.telegrambot.domain.CommandType.*;

@Slf4j
public class AdminCommandHandler implements CommandHandler {
    private final AuthService authService;
    private final CommandDispatcher dispatcher;
    private final Map<String, Boolean> waitingForAuth = new ConcurrentHashMap<>();

    public AdminCommandHandler(AuthService authService, CommandDispatcher dispatcher) {
        this.authService = authService;
        this.dispatcher = dispatcher;
    }

    @Override
    public List<SendMessage> handle(Update update) {
        var chatId = update.getMessage().getChatId().toString();
        var text = update.getMessage().getText().trim();

        // Если ожидаем пароль от этого чата
        if (waitingForAuth.containsKey(chatId)) {
            return handlePassword(update);
        }

        if (text.equals(ADMIN.getText())) {
            if (authService.isAuthorized(chatId)) {
                var msg = SendMessage.builder()
                        .chatId(chatId)
                        .text("✅ Вы уже авторизованы как менеджер.")
                        .build();
                return List.of(msg);
            } else {
                waitingForAuth.put(chatId, true);
                dispatcher.setActiveDialog(chatId, this);
                var msg = SendMessage.builder()
                        .chatId(chatId)
                        .text("🔐 Введите пароль для авторизации менеджера:")
                        .build();
                return List.of(msg);
            }
        }
        return List.of();
    }

    private List<SendMessage> handlePassword(Update update) {
        var chatId = update.getMessage().getChatId().toString();
        var password = update.getMessage().getText().trim();
        waitingForAuth.remove(chatId);
        dispatcher.removeActiveDialog(chatId);

        if (authService.authorize(chatId, password)) {
            var commands = CommandType.getCommands(true);
            var commandsStr = commands.stream()
                    .map(CommandType::getDisplayText)
                    .collect(Collectors.joining("\n"));
            var text = """
                ✅ Авторизация успешна!
                Теперь вам доступны команды статистики:
                %s
                Используйте %s для просмотра меню.
                """.formatted(commandsStr, START.getText());
            var msg = SendMessage.builder().chatId(chatId).text(text).build();
            return List.of(msg);
        } else {
            var msg = SendMessage.builder()
                    .chatId(chatId)
                    .text("❌ Неверный пароль. Доступ запрещён.")
                    .build();
            return List.of(msg);
        }
    }
}