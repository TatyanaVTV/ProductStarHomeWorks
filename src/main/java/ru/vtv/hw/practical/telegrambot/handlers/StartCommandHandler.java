package ru.vtv.hw.practical.telegrambot.handlers;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vtv.hw.practical.telegrambot.domain.CommandType;
import ru.vtv.hw.practical.telegrambot.service.AuthService;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static ru.vtv.hw.practical.telegrambot.domain.CommandType.ADMIN;

@Slf4j
public class StartCommandHandler implements CommandHandler {
    private final AuthService authService;

    public StartCommandHandler(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public List<SendMessage> handle(Update update) {
        var chatId = update.getMessage().getChatId();
        var userId = chatId.toString();
        boolean isAuthorized = authService.isAuthorized(userId);

        var userCommands = CommandType.getCommands(false);
        var userCommandsStr = userCommands.stream()
                .filter(cmd -> !(isAuthorized && ADMIN.equals(cmd)))
                .map(CommandType::getDisplayText)
                .collect(Collectors.joining("\n"));

        String text;
        if (isAuthorized) {
            var adminCommands = CommandType.getCommands(true);
            var adminCommandsStr = adminCommands.stream()
                    .map(CommandType::getDisplayText)
                    .collect(Collectors.joining("\n"));
            text = format("🤖 Добро пожаловать!\n\n🛠️ Доступные команды:\n%s\n\n%s", userCommandsStr, adminCommandsStr);
        } else {
            text = format("🤖 Добро пожаловать!\n\n🛠️ Доступные команды:\n%s", userCommandsStr);
        }
        var msg = SendMessage.builder().chatId(chatId).text(text).build();
        return List.of(msg);
    }
}
