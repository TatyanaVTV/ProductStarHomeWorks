package base.strings;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class HomeWork {
    private static final String LETTERS_ONLY_REGEX = "[^a-zA-Zа-яёА-ЯЁ\s]";
    public static void main(String[] args) {
        task1();
        task2();
        task3();
    }

    public static void task1() {
        String rowToCheck = "В тексте, который вы видите на этом изображении, посчитайте количество букв 'е' в каждом слове.";
        char symbolToCheck = 'е';

        System.out.println("=== Проверка количества букв '" + symbolToCheck + "' в словах ====");
        Object[][] results = checkRow(rowToCheck, symbolToCheck);
        printResults(results);

        System.out.println(System.lineSeparator() + "===== Альтернативный вариант (без стримов) =====");
        Object[][] resultsWithoutStreams = checkRowWithoutStreams(rowToCheck, symbolToCheck);
        printResultsWithoutStreams(resultsWithoutStreams);

    }

    private static Object[][] checkRowWithoutStreams(String rowToCheck, char symbolToCount) {
        String[] words = rowToCheck.replaceAll(LETTERS_ONLY_REGEX,  "").split("\s");
        Object[][] result = new Object[words.length][words.length];

        for (int i = 0; i < words.length; i++) {
            result[i] = new Object[]{words[i], countSpecificSymbolInTheRowWithoutStreams(words[i], symbolToCount)};
        }

        return result;
    }

    private static int countSpecificSymbolInTheRowWithoutStreams(String word, char charToCheck) {
        int count = 0;
        for (char c : word.toCharArray()) {
            if (c == charToCheck) count++;
        }
        return count;
    }

    private static void printResultsWithoutStreams(Object[][] results) {
        StringBuilder sb = new StringBuilder();
        int count = 0;

        for (Object[] result : results) {
            count += (int) result[1];
            sb.append(result[0]);
            sb.append(" => ");
            sb.append(result[1]);
            sb.append(System.lineSeparator());
            System.out.print(sb);
            sb.setLength(0);
        }
        sb.append("Общее количество: ");
        sb.append(count);
        System.out.println(sb);
    }

    private static Object[][] checkRow(String rowToCheck, char symbolToCount) {
        String[] words = rowToCheck.replaceAll(LETTERS_ONLY_REGEX,  "").split("\s");
        Object[][] result = new Object[words.length][words.length];

        for (int i = 0; i < words.length; i++) {
            result[i] = new Object[]{words[i], countSpecificSymbolInTheRow(words[i], symbolToCount)};
        }

        return result;
    }

    private static void printResults(Object[][] results) {
        StringBuilder sb = new StringBuilder();
        AtomicInteger count = new AtomicInteger();
        Arrays.stream(results).forEach(result -> {
            count.addAndGet((int) result[1]);
            sb.append(result[0]);
            sb.append(" -> ");
            sb.append(result[1]);
            sb.append(System.lineSeparator());
            System.out.print(sb);
            sb.setLength(0);
        });
        sb.append("Общее количество: ");
        sb.append(count);
        System.out.println(sb);
    }

    private static int countSpecificSymbolInTheRow(String word, char charToCheck) {
        return (int) word.chars().mapToObj(c -> (char) c).filter(checkedChar -> checkedChar == charToCheck).count();
    }

    public static void task2() {
        String invalidPhoneNumber = "Тестовая строка";
        String validPhoneNumber = "+74951234567";
        StringBuilder str = new StringBuilder(System.lineSeparator())
                .append("=== Проверка корректности телефонного номера ===")
                .append(System.lineSeparator())
                .append("Checked value: ")
                .append(invalidPhoneNumber)
                .append(System.lineSeparator())
                .append("Expected value:  false")
                .append(System.lineSeparator())
                .append("Function result: ")
                .append(checkIfValidPhoneNumber(invalidPhoneNumber))
                .append(System.lineSeparator())
                .append(System.lineSeparator())
                .append("Checked value: ")
                .append(validPhoneNumber)
                .append(System.lineSeparator())
                .append("Expected value:  true")
                .append(System.lineSeparator())
                .append("Function result: ")
                .append(checkIfValidPhoneNumber(validPhoneNumber))
                ;
        System.out.println(str);
    }

    private static boolean checkIfValidPhoneNumber(String valueToCheck) {
        return valueToCheck.matches("^\\+[7|8]?[0-9]{10}");
    }

    public static void task3() {
        String testString = "Тестовая строка! Test! 123!";
        StringBuilder str = new StringBuilder(System.lineSeparator())
                .append("=== Удаление всех букв и пробелов из текста ====")
                .append(System.lineSeparator())
                .append(testString)
                .append(System.lineSeparator())
                .append("Expected value:  !!123!")
                .append(System.lineSeparator())
                .append("Function result: ")
                .append(removeAllLettersAndSpaces(testString));
        System.out.println(str);
    }

    private static String removeAllLettersAndSpaces(String originalText) {
        return originalText.replaceAll("[a-zA-Zа-яёА-ЯЁ\s]", "");
    }
}