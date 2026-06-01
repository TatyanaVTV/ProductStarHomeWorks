package ru.vtv.hw.practical.telegrambot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.vtv.hw.practical.telegrambot.repository.RequestRepositoryImpl;
import ru.vtv.hw.practical.telegrambot.service.AuthServiceImpl;
import ru.vtv.hw.practical.telegrambot.service.CreditServiceImpl;

import static java.util.Objects.isNull;

@Slf4j
public class App {
    private static final String BOT_TOKEN_ERROR =
            "Ошибка: не задан токен бота. Установите переменную окружения BOT_TOKEN";
    private static final String BOT_USERNAME_ERROR =
            "Ошибка: не задано имя бота. Установите переменную окружения BOT_USERNAME";

    public static void main(String[] args) {
        log.info("Запуск приложения...");

        var repository = new RequestRepositoryImpl();
        var creditService = new CreditServiceImpl(repository);
        var authService = new AuthServiceImpl();

        var botToken = System.getenv("BOT_TOKEN");
        checkValue(botToken, BOT_TOKEN_ERROR);

        var botUsername = System.getenv("BOT_USERNAME");
        checkValue(botUsername, BOT_USERNAME_ERROR);

        var bot = new CreditBot(botUsername, botToken, creditService, authService);
        log.info("Запуск бота...");
        try {
            var botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
            log.warn("Бот @{} успешно запущен!", botUsername);
        } catch (TelegramApiException e) {
            log.error("Ошибка запуска: {}", e.getMessage());
        }
    }

    private static void checkValue(String value, String errorMsg) {
        if (isNull(value) || value.isEmpty()) {
            log.error(errorMsg);
            System.exit(1);
        }
    }
}
