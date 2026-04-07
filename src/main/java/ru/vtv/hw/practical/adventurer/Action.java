package ru.vtv.hw.practical.adventurer;

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
    ADD_ITEM(1, true),
    CHANGE_ITEM_COUNT(2, true),
    REMOVE_ITEM(3, true),
    SEARCH_ITEM(4, true),
    SHOW_INVENTORY(5, false),
    EXIT(7, false),
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
