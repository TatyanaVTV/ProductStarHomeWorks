package base.firstprogramm;

import java.util.Scanner;

public class Calculator {

    public static final String EXIT_COMMAND = "выход";
    private static Result[] results = new Result[10];

    public static void main(String[] args) {
        try(var reader = new Scanner(System.in)) {
            for (int i = 0; i < results.length; i++) {
                double first;
                double second;
                try {
                    System.out.print("\nВведите 2 числа: ");
                    first = readValue(reader);
                    second = readValue(reader);
                } catch (ExitInitializedException ex) {
                    System.out.println(ex.getLocalizedMessage());
                    printResults();
                    return;
                } catch (NumberFormatException ex) {
                    continue;
                }

                System.out.print("Введите оператор: (+, -, *, /): ");
                char operator = reader.next().charAt(0);

                results[i] = calculate(first, second, operator);
                if (results[i] == null) i--;
            }
            System.out.println("\nПроизведено максимальное количество расчётов.");
            printResults();
        }
    }

    private static double readValue(Scanner reader) throws ExitInitializedException {
        String typedCommand = reader.next();
        if (typedCommand.equals(EXIT_COMMAND)) {
            throw new ExitInitializedException();
        }

        try {
            return Double.valueOf(typedCommand);
        } catch (NumberFormatException ex) {
            System.out.println("Неверный формат числа или неизвестная команда! Повторите ввод двух чисел: ");
            throw ex;
        }
    }

    private static void printResults() {
        System.out.println("\nПосчитанные результаты: ");
        for (Result result : results) {
            if (result == null) continue;
            System.out.println(result);
        }
    }

    private static Result calculate(double first, double second, char operator){
        double result = 0.0;

        switch (operator) {
            case '+':
                result = first + second;
                break;

            case '-':
                result = first - second;
                break;

            case '*':
                result = first * second;
                break;

            case '/':
                result = first / second;
                break;

            default:
                System.out.printf("Введите корректный оператор");
                return null;
        }
        System.out.printf("%.1f %c %.1f = %.1f\n", first, operator, second, result);
        return new Result(first, second, operator, result);
    }
}

class Result {
    private double first;
    private double second;
    private char operator;
    private double result;

    public Result(double first, double second, char operator, double result) {
        this.first = first;
        this.second = second;
        this.operator = operator;
        this.result = result;
    }

    @Override
    public String toString() {
        return first + " " + operator + " " + second + " = " + result; // Переделано из StringBuilder'а, т.к. показалось, что он здесь избыточен.
        /*return new StringBuilder()
                .append(first)
                .append(" ")
                .append(operator)
                .append(" ")
                .append(second)
                .append(" = ")
                .append(result)
                .toString(); */
    }
}

/*
 *     ДЗ
 *     1. добавьте массив для сохранения результатов размерностью 10
 *     если результатов стало больше мы завершаем работы, информируя пользователя и распечатывая результаты
 *
 *     2. поместите код в цикл для возможности использования без постоянного запуска программы.
 *     Для выхода пусть буду слова "выход"
 *     T.е. пользователь ввел выход - мы просто выходим, сохраняя результат в массиве результатов и выводим массив на консоль.
 *
 *
 */