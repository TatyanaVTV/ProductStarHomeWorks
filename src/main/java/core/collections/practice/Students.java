package core.collections.practice;

import java.util.Scanner;

public class Students {
    private static StudentCommandHandler STUDENT_COMMAND_HANDLER = new StudentCommandHandler();
    public static void main(String[] args) {
        while (true) {
            printMessage();
            Command command = readCommand();
            if (command.getAction() == Action.EXIT) {
                return;
            } else if (command.getAction() == Action.ERROR) {
                continue;
            } else {
                STUDENT_COMMAND_HANDLER.processCommand(command);
            }
        }
    }

    private static void printMessage() {
        System.out.println("----------------------");
        System.out.println("0. Выход");
        System.out.println("1. Создание данных");
        System.out.println("2. Обновление данных");
        System.out.println("3. Удаление данных");
        System.out.println("4. Вывод статистики по курсам");
        System.out.println("5. <В разработке>");
        System.out.println("6. Поиск по фамилии");
        System.out.println("----------------------");
    }

    private static Command readCommand() {
        Scanner scanner = new Scanner(System.in);
        try {
            String code = scanner.nextLine();
            Integer actionCode = Integer.valueOf(code);
            Action action = Action.fromCode(actionCode);
            if (action.isRequireAdditionalData()) {
                String data = scanner.nextLine();
                return new Command(action, data);
            } else {
                return new Command(action);
            }
        } catch (Exception ex) {
            System.out.printf("Проблема обработки ввода. %s\n", ex.getLocalizedMessage());
        }
        return new Command(Action.ERROR);
    }
}
