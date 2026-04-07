package ru.vtv.hw.practical.story;

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
    CREATE_CHARACTER(1, true),
    CREATE_ACTION(2, true),
    CREATE_PLACE(3, true),
    GENERATE_STORY(4, false),
    SHOW_STORY_DATA(5, false),
    SHOW_GENERATED_STORIES(6, false),
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
