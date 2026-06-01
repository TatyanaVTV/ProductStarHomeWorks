package ru.vtv.hw.practical.telegrambot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum CommandType {
    START("/start", "", "главное меню", false),
    CALCULATE("/calculate", "", "расчёт графика платежей", false),
    HISTORY("/history", "", "история ваших расчётов", false),
    ADMIN("/admin", "", "авторизация для доступа к статистике", false),
    STATS("/stats", "", "общая статистика", true),
    STATS_TYPES("/types", "", "статистика по типам платежей", true),
    STATS_RANGE("/range", "<min> <max>", "поиск по диапазону сумм", true),
    STATS_TOTAL("/total", "", "общая сумма кредитов", true);

    private final String text;
    private final String params;
    private final String description;
    private final boolean needAuthorization;

    public String getDisplayText() {
        var command = params.isEmpty() ? text : text + " " + params;
        return command + " - " + description;
    }

    @Override
    public String toString() {
        return text;
    }

    public static List<CommandType> getCommands(boolean forAuthorizedUser) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.needAuthorization == forAuthorizedUser)
                .toList();
    }
}
