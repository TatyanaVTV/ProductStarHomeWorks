package ru.vtv.hw.practical.multigenerator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;
import static ru.vtv.hw.practical.multigenerator.IntGenerator.generateListOfNumbers;
import static ru.vtv.hw.practical.multigenerator.IntGenerator.generateNumber;

@Execution(CONCURRENT)
public class IntGeneratorTest {

    @Test
    void testGenerateListOfNumbersValidInput() {
        var count = 5;
        var min = 10;
        var max = 20;

        var result = generateListOfNumbers(count, min, max);

        assertEquals(count, result.size());
        result.stream()
                .map(num -> num >= min && num <= max)
                .forEach(Assertions::assertTrue);
    }

    @Test
    void testGenerateListOfNumbersZeroCount() {
        var result = generateListOfNumbers(0, 1, 100);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGenerateListOfNumbersNegativeCount() {
        assertThrows(IllegalArgumentException.class, () -> generateListOfNumbers(-1, 1, 10));
    }

    @Test
    void testGenerateListOfNumbersMinGreaterThanMax() {
        assertThrows(IllegalArgumentException.class, () -> generateListOfNumbers(5, 20, 10));
    }

    @Test
    void testGenerateNumberValidInput() {
        var min = 50;
        var max = 100;

        IntStream.range(0, 100)
                .map(_ -> generateNumber(min, max))
                .mapToObj(num -> num >= min && num <= max)
                .forEach(Assertions::assertTrue);
    }

    @Test
    void testGenerateNumberMinEqualsMax() {
        var value = generateNumber(42, 42);
        assertEquals(42, value);
    }

    @Test
    void testGenerateNumberMinGreaterThanMax() {
        assertThrows(IllegalArgumentException.class, () -> generateNumber(100, 50));
    }

    @Test
    void testCheckCountNegative() {
        var exception = assertThrows(IllegalArgumentException.class, () -> {
            var method = IntGenerator.class.getDeclaredMethod("checkCount", int.class);
            method.setAccessible(true);
            try {
                method.invoke(null, -5);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
        assertTrue(exception.getMessage().contains("Count не может быть отрицательным"));
    }

    @Test
    void testCheckCountZero() {
        assertDoesNotThrow(() -> {
            var method = IntGenerator.class.getDeclaredMethod("checkCount", int.class);
            method.setAccessible(true);
            method.invoke(null, 0);
        });
    }

    @Test
    void testCheckMinMaxMinGreaterThanMax() {
        var exception = assertThrows(IllegalArgumentException.class, () -> {
            var method = IntGenerator.class.getDeclaredMethod("checkMinMax", int.class, int.class);
            method.setAccessible(true);
            try {
                method.invoke(null, 50, 30);
            } catch (InvocationTargetException e) {
                throw e.getCause(); // Перебрасываем исходное исключение
            }
        });
        assertTrue(exception.getMessage().contains("Min (50) не может быть больше max (30)"));
    }

    @Test
    void testCheckMinMaxEqualValues() {
        assertDoesNotThrow(() -> {
            var method = IntGenerator.class.getDeclaredMethod("checkMinMax", int.class, int.class);
            method.setAccessible(true);
            method.invoke(null, 42, 42);
        });
    }

    @Test
    void testCheckMinMaxNormalRange() {
        assertDoesNotThrow(() -> {
            var method = IntGenerator.class.getDeclaredMethod("checkMinMax", int.class, int.class);
            method.setAccessible(true);
            method.invoke(null, 10, 20);
        });
    }
}
