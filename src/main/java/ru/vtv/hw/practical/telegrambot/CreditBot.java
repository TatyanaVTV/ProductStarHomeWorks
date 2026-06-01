package ru.vtv.hw.practical.telegrambot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.vtv.hw.practical.telegrambot.service.AuthService;
import ru.vtv.hw.practical.telegrambot.service.CreditService;

import static org.telegram.telegrambots.meta.api.methods.ActionType.TYPING;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CreditBot extends TelegramLongPollingBot {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final CommandDispatcher commandDispatcher;
    private final String botUsername;
    private final String botToken;

    public CreditBot(String botUsername, String botToken, CreditService creditService, AuthService authService) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.commandDispatcher = new CommandDispatcher(creditService, authService);

        try {
            clearWebhook();
        } catch (TelegramApiException e) {
            log.error("Ошибка удаления Webhook: {}", e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        executor.submit(() -> {
            if (update.hasMessage() && update.getMessage().hasText()) {
                var chatId = update.getMessage().getChatId();
                sendChatAction(chatId, TYPING);

                var responses = commandDispatcher.dispatch(update);
                if (responses != null) {
                    for (var msg : responses) {
                        try {
                            execute(msg);
                        } catch (TelegramApiException e) {
                            log.error("Ошибка отправки сообщения: {}", e.getMessage());
                        }
                    }
                }
            }
        });

    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    private void sendChatAction(long chatId, ActionType actionType) {
        try {
            log.debug("Отправка ChatAction: {}", actionType);
            var action = SendChatAction.builder()
                    .chatId(chatId)
                    .action(actionType.toString())
                    .build();
            execute(action);
        } catch (TelegramApiException e) {
            log.debug("Отправка ChatAction {} не удалась: {}", actionType, e.getMessage());
        }
    }
}
