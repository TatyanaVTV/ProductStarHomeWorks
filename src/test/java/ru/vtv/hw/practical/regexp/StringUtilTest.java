package ru.vtv.hw.practical.regexp;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static ru.vtv.hw.practical.regexp.StringUtil.clearString;

@TestInstance(PER_CLASS)
public class StringUtilTest {

    @ParameterizedTest
    @MethodSource("getTestString")
    public void testStringUtils(String originalString, String expectedString, String errorMsg) {
        assertEquals(expectedString, clearString(originalString), errorMsg);
    }

    private Stream<Arguments> getTestString() {
        return Stream.of(
                Arguments.of(null, null, "Для пустого значения должен возвращаться null!"),
                Arguments.of("", "", "Для пустой строки должна возвращаться пустая строка!"),

                Arguments.of("Мой номер телефона: +7 (999) 123-45-67, а почтовый индекс: 123456.",
                        "+7(999)123-45-67,123456.",
                        "Должны быть удалены буквы, пробелы и двоеточия!"),

                Arguments.of("олрпорплропа76ге5765лор",
                        "765765",
                        "Должны быть удалены буквы, пробелы и двоеточия!")
        );
    }
}
