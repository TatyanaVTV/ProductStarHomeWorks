package ru.vtv.hw.practical.multigenerator;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.String.format;

@UtilityClass
public class IntGenerator {
    private static final Random RND = new Random();

    public static List<Integer> generateListOfNumbers(int count, int min, int max) {
        checkCount(count);
        checkMinMax(min, max);

        return RND.ints(count, min, max + 1)
                .boxed()
                .collect(Collectors.toList());
    }

    public static int generateNumber(int min, int max) {
        checkMinMax(min, max);
        return min + RND.nextInt(max - min + 1);
    }

    private void checkCount(int count) {
        if (count < 0) throw new IllegalArgumentException("Count не может быть отрицательным: " + count);
    }

    private void checkMinMax(int min, int max) {
        if (min > max) throw new IllegalArgumentException(format("Min (%d) не может быть больше max (%d)", min, max));
    }
}
