package ru.vtv.hw.practical.detective;

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
    ADD_EVIDENCE(1, true),
    CHECK_EVIDENCE(2, true),
    REMOVE_EVIDENCE(3, true),
    COMPARE_EVIDENCE(4, true),
    SHOW_EVIDENCES(5, false),
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
