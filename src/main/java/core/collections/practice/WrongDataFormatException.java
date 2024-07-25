package core.collections.practice;

import org.jetbrains.annotations.NotNull;

public class WrongDataFormatException extends Exception {
    public WrongDataFormatException(Command command, String format, Exception cause) {
        super(getCommandDataFormatExceptionMsg(command, format, cause));
    }

    @NotNull
    private static String getCommandDataFormatExceptionMsg(Command command, String format, Exception e) {
        return String.format("[ERROR] Данные для команды %s должны быть в формате \"%s\" (%s: %s)%s"
                , command.getAction().name()
                , format
                , e.getClass().getSimpleName()
                , e.getLocalizedMessage()
                , System.lineSeparator());
    }
}
