package ru.vtv.hw.practical.regexp;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static ru.vtv.hw.practical.regexp.UserNameValidator.validate;

@TestInstance(PER_CLASS)
public class UserNameValidatorTest {
    private static final UserNameValidationResult INVALID = new UserNameValidationResult(false, null);
    private static final String VALID_MSG =
            "Должен быть возвращен положительный результат с корректно отформатированным логином!";

    @ParameterizedTest
    @MethodSource("getLogins")
    public void testUserNameValidator(String givenLogin, UserNameValidationResult expectedResult, String errorMsg) {
        assertEquals(expectedResult, validate(givenLogin), errorMsg);
    }

    private Stream<Arguments> getLogins() {
        return Stream.of(
                Arguments.of("User__NAME123",
                        new UserNameValidationResult(true, "user_name123"),
                        VALID_MSG),
                Arguments.of("aBcDeFgHi__123456789",
                        new UserNameValidationResult(true, "abcdefghi_123456789"),
                        VALID_MSG),

                Arguments.of(null, INVALID,
                        "Для пустого значения должен возвращаться отрицательный результат!"),
                Arguments.of("", INVALID,
                        "Для пустой строки должен возвращаться отрицательный результат!"),
                Arguments.of("123user", INVALID,
                        "Для логина, начинающегося не с буквы, должен возвращаться отрицательный результат!"),
                Arguments.of("ab", INVALID,
                        "Для логина короче 3 символов должен возвращаться отрицательный результат!"),
                Arguments.of("U_Тест_!№;%:?*()", INVALID,
                        "Для логина с недопустимыми символами возвращаться отрицательный результат!"),
                Arguments.of("abcdefghij_1234567890", INVALID,
                        "Для логина длинее 20 символов должен возвращаться отрицательный результат!")
        );
    }
}
