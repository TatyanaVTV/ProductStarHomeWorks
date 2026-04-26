package ru.vtv.hw.practical.niofile.menu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

/**
 * Представляет собой вариант, выбранный пользователем
 * */
@Getter
@RequiredArgsConstructor
public enum Action {
    ADD_USER(1, true),
    SHOW_ALL_USERS(2, true),
    EXIT(3, false),
    ERROR(-1, false);

    private final Integer code;
    private final boolean requireAdditionalData;

    public static Action fromCode(Integer code) {
        return Arrays.stream(Action.values())
                .filter(action -> Objects.equals(action.getCode(), code))
                .findFirst()
                .orElseGet(() -> {
                    System.out.println("Неизвестный код действия " + code);
                    return ERROR;
                });
    }
}
