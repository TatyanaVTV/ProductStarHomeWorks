package ru.vtv.hw.practical.telegrambot.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuthServiceImpl implements AuthService {
    private final Map<String, Boolean> authorizedUsers = new ConcurrentHashMap<>();

    @Override
    public boolean isAuthorized(String userId) {
        return authorizedUsers.getOrDefault(userId, false);
    }

    @Override
    public boolean authorize(String userId, String password) {
        var managerPswd = System.getenv("MANAGER_PASSWORD");
        if (managerPswd.equals(password)) {
            authorizedUsers.put(userId, true);
            return true;
        }
        return false;
    }
}
