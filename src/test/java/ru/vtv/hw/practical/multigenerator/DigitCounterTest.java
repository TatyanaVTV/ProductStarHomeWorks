package ru.vtv.hw.practical.multigenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;
import static ru.vtv.hw.practical.multigenerator.IntGenerator.generateListOfNumbers;

@Execution(CONCURRENT)
public class DigitCounterTest {
    public DigitCounter digitCounter;

    @BeforeEach
    public void setUp() {
        digitCounter = new DigitCounter();
    }

    @Test
    void testEmptyList() throws InterruptedException {
        List<Integer> emptyList = List.of();

        digitCounter.processNumbers(emptyList);

        assertEquals(0, digitCounter.getTwoDigitCount(), "Двузначные числа должны быть 0 для пустого списка");
        assertEquals(0, digitCounter.getThreeDigitCount(), "Трёхзначные числа должны быть 0 для пустого списка");
        assertEquals(0, digitCounter.getFourDigitCount(), "Четырёхзначные числа должны быть 0 для пустого списка");
    }

    @Test
    void testOnlyOneDigitNumbers() throws InterruptedException {
        var oneDigitNumbers = Arrays.asList(1, 2, 3);

        digitCounter.processNumbers(oneDigitNumbers);

        assertEquals(0, digitCounter.getTwoDigitCount(), "Не должно быть двузначных чисел");
        assertEquals(0, digitCounter.getThreeDigitCount(), "Не должно быть трёхзначных чисел");
        assertEquals(0, digitCounter.getFourDigitCount(), "Не должно быть четырёхзначных чисел");
    }

    @Test
    void testOnlyTwoDigitNumbers() throws InterruptedException {
        var twoDigitNumbers = Arrays.asList(10, 15, 25, 99);

        digitCounter.processNumbers(twoDigitNumbers);

        assertEquals(4, digitCounter.getTwoDigitCount(), "Должно быть 4 двузначных числа");
        assertEquals(0, digitCounter.getThreeDigitCount(), "Не должно быть трёхзначных чисел");
        assertEquals(0, digitCounter.getFourDigitCount(), "Не должно быть четырёхзначных чисел");
    }

    @Test
    void testOnlyThreeDigitNumbers() throws InterruptedException {
        var threeDigitNumbers = Arrays.asList(100, 500, 999);

        digitCounter.processNumbers(threeDigitNumbers);

        assertEquals(0, digitCounter.getTwoDigitCount(), "Не должно быть двузначных чисел");
        assertEquals(3, digitCounter.getThreeDigitCount(), "Должно быть 3 трёхзначных числа");
        assertEquals(0, digitCounter.getFourDigitCount(), "Не должно быть четырёхзначных чисел");
    }

    @Test
    void testOnlyFourDigitNumbers() throws InterruptedException {
        var fourDigitNumbers = Arrays.asList(1000, 2000, 3000, 4000, 5000);

        digitCounter.processNumbers(fourDigitNumbers);

        assertEquals(0, digitCounter.getTwoDigitCount(), "Не должно быть двузначных чисел");
        assertEquals(0, digitCounter.getThreeDigitCount(), "Не должно быть трёхзначных чисел");
        assertEquals(5, digitCounter.getFourDigitCount(), "Должно быть 5 четырёхзначных чисел");
    }

    @Test
    void testMixedNumbers() throws InterruptedException {
        var mixedNumbers = Arrays.asList(
                5,           // однозначное — игнорируется
                15,          // двузначное
                25,          // двузначное
                99,          // двузначное
                100,       // трёхзначное
                500,       // трёхзначное
                999,       // трёхзначное
                1000,      // четырёхзначное
                2000,      // четырёхзначное
                9999,      // четырёхзначное
                10_000     // пятизначное — игнорируется
        );

        digitCounter.processNumbers(mixedNumbers);

        assertEquals(3, digitCounter.getTwoDigitCount(), "Должно быть 3 двузначных числа (15, 25, 99)");
        assertEquals(3, digitCounter.getThreeDigitCount(), "Должно быть 3 трёхзначных числа (100, 500, 999)");
        assertEquals(3, digitCounter.getFourDigitCount(), "Должно быть 3 четырёхзначных числа (1000, 2000, 9999)");
    }

    @Test
    void testBoundaryValues() throws InterruptedException {
        var boundaryNumbers = Arrays.asList(
                9,    // ниже диапазона двузначных
                10,   // нижняя граница двузначных
                99,   // верхняя граница двузначных
                100,  // нижняя граница трёхзначных
                999,  // верхняя граница трёхзначных
                1000, // нижняя граница четырёхзначных
                9999,   // верхняя граница четырёхзначных
                10_000   // выше диапазона четырёхзначных
        );

        digitCounter.processNumbers(boundaryNumbers);

        assertEquals(2, digitCounter.getTwoDigitCount(), "Должны быть учтены 10 и 99");
        assertEquals(2, digitCounter.getThreeDigitCount(), "Должны быть учтены 100 и 999");
        assertEquals(2, digitCounter.getFourDigitCount(), "Должны быть учтены 1000 и 9999");
    }

    @Test
    void testNegativeNumbers() throws InterruptedException {
        var negativeNumbers = Arrays.asList(-5, -15, -99, -100);

        digitCounter.processNumbers(negativeNumbers);

        assertEquals(0, digitCounter.getTwoDigitCount(), "Отрицательные числа не должны считаться двузначными");
        assertEquals(0, digitCounter.getThreeDigitCount(), "Отрицательные числа не должны считаться трёхзначными");
        assertEquals(0, digitCounter.getFourDigitCount(), "Отрицательные числа не должны считаться четырёхзначными");
    }

    @Test
    void testMultipleCallsToSameInstance() throws InterruptedException {
        var firstList = Arrays.asList(15, 125, 1_999);
        var secondList = Arrays.asList(100, 20);

        digitCounter.processNumbers(firstList);
        digitCounter.processNumbers(secondList);

        assertEquals(2, digitCounter.getTwoDigitCount(), "После двух вызовов должно быть 2 двузначных числа");
        assertEquals(2, digitCounter.getThreeDigitCount(), "После двух вызовов должно быть 2 трёхзначных числа");
        assertEquals(1, digitCounter.getFourDigitCount(), "После двух вызовов должно быть 1 тетырёхзначное число");
    }

    @Test
    void testLargeListPerformance() throws InterruptedException {
        var count = 100_000;
        var largeList = generateListOfNumbers(count, 10, 9_999);

        digitCounter.processNumbers(largeList);
        var totalCount = digitCounter.getTwoDigitCount() + digitCounter.getThreeDigitCount() + digitCounter.getFourDigitCount();

        assertEquals(count, largeList.size());
        assertEquals(count, totalCount);
    }
}
