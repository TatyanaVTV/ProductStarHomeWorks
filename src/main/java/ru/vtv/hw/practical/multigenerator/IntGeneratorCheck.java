package ru.vtv.hw.practical.multigenerator;

import lombok.SneakyThrows;

import static ru.vtv.hw.practical.multigenerator.IntGenerator.generateListOfNumbers;

public class IntGeneratorCheck {
    @SneakyThrows
    public static void main(String[] args) {
        var numbers = generateListOfNumbers(100_000, 10, 9_999);

        var counter = new DigitCounter();
        counter.processNumbers(numbers);

        System.out.printf("Двузначные числа: %d%n", counter.getTwoDigitCount());
        System.out.printf("Трёхзначные числа: %d%n", counter.getThreeDigitCount());
        System.out.printf("Четырёхзначные числа: %d%n", counter.getFourDigitCount());
    }
}
