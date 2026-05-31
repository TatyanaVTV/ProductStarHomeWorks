package ru.vtv.hw.practical.telegrambot.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface CommandHandler {
    List<SendMessage> handle(Update update);
}
