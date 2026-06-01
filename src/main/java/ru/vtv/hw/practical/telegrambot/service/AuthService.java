package ru.vtv.hw.practical.telegrambot.service;

public interface AuthService {
    boolean isAuthorized(String userId);
    boolean authorize(String userId, String password);
}
