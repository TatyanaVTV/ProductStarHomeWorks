package ru.vtv.hw.practical.regexp;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static ru.vtv.hw.practical.regexp.PhoneValidator.validate;

@TestInstance(PER_CLASS)
public class PhoneValidatorTest {
    private final static String VALID_MSG = "Указанный номер телефона должен считаться валидным!";

    @ParameterizedTest
    @MethodSource("getPhoneNumbers")
    public void testPhoneValidator(String phoneNumber, boolean isValid, String msg) {
        assertEquals(isValid, validate(phoneNumber), msg);
    }

    private Stream<Arguments> getPhoneNumbers() {
        return Stream.of(
                // valid
                Arguments.of("+1-1-12345", true, VALID_MSG),
                Arguments.of("+123-123-12345678", true, VALID_MSG),
                Arguments.of("+7 99 12345", true, VALID_MSG),
                Arguments.of("+7-9-123456", true, VALID_MSG),
                Arguments.of("+790312345678", true, VALID_MSG),
                Arguments.of("+44 20 7946 0987", true, VALID_MSG),
                Arguments.of("+123 45 6789012", true, VALID_MSG),
                Arguments.of("+7 999 123-45-67", true, VALID_MSG),
                Arguments.of("+79991234567", true, VALID_MSG),
                Arguments.of("+7--999--123--45--67", true, VALID_MSG),
                Arguments.of("+7-999-123-45-67", true, VALID_MSG),
                Arguments.of("+7  99  12345678", true, VALID_MSG),
                Arguments.of("+7 999 1234567", true, VALID_MSG),
                Arguments.of("+1-555-123-4567", true, VALID_MSG),
                Arguments.of("+441234567890", true, VALID_MSG),

                // not valid
                Arguments.of(null, false, "Пустой номер должен считаться невалидным!"),
                Arguments.of("", false, "Пустой номер должен считаться невалидным!"),
                Arguments.of("441234567890", false, "Номер без + в начале должен считаться невалидным"),
                Arguments.of("withletters", false, "Номер содержащий буквы должен считаться невалидным"),
                Arguments.of("*#symbols%@", false, "Номер содержащий символы должен считаться невалидным"),
                Arguments.of("+790312345", false,
                        "Номер без разделителей должен быть длиной от 11 до 14 символов, код страны и код города = 3 цифры!"),

                Arguments.of("+1234-123-123456", false,
                        "Номер, у которого более 3 символов в коде страны, должен считаться невалидным"),
                Arguments.of("+123-1234-123456", false,
                        "Номер, у которого более 3 символов в коде города/оператора, должен считаться невалидным"),
                Arguments.of("+123-123-1234567489", false,
                        "Номер, у которого более 8 символов в основном номере, должен считаться невалидным"),
                Arguments.of("+123-123-1234", false,
                        "Номер, у которого менее 5 символов в основном номере, должен считаться невалидным")
        );
    }
}
