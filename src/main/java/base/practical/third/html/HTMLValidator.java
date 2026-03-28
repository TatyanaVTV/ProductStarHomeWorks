package base.practical.third.html;

import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

public class HTMLValidator {
    private static final Pattern TAG_PATTERN = Pattern.compile("<(/?)(\\w+)>");

    private static final List<String> SELF_CLOSING_TAGS = List.of("br", "img");

    public ValidationResult validate(String html) {
        var tagStack = new Stack<String>();

        var matcher = TAG_PATTERN.matcher(html);

        while (matcher.find()) {
            var slash = matcher.group(1); // "/", если есть
            var tag = matcher.group(2).toLowerCase(); // сам тэг

            if (slash.isEmpty()) {
                // если "/" отсутствует => тэг открывающий, добавляем его в стэк
                // но только если это не самозакрывающийся тэг и не комментарий (их не отслеживаем)
                if (!SELF_CLOSING_TAGS.contains(tag) && !tag.startsWith("!--")) {
                    tagStack.push(tag);
                }
            } else {
                // если "/" не пустой => тэг закрывающий
                // если стэк пустой => открывающего тэга не было, html не валидный
                if (tagStack.isEmpty()) {
                    var error = String.format("Закрывающий тег без открывающего (</%s>)", tag);
                    return new ValidationResult(false, error);
                }

                // проверка, что тэги закрываются в корректном порядке
                var lastOpenedTag = tagStack.pop();
                if (!lastOpenedTag.equals(tag)) {
                    var error = String.format("Несоответствие тэгов: ожидался </%s>, получен </%s>", lastOpenedTag, tag);
                    return new ValidationResult(false, error);
                }
            }
        }

        if (!tagStack.isEmpty()) {
            return new ValidationResult(false, "Незакрытые теги: " + tagStack);
        }

        return new ValidationResult(true, null);
    }
}
