package core.collections.practice;

import java.util.Scanner;

import static core.collections.practice.Action.CREATE;

public class Students {
    private static StudentCommandHandler STUDENT_COMMAND_HANDLER = new StudentCommandHandler();
    public static void main(String[] args) throws WrongDataFormatException {
        prepareSomeStudentsForStatsTest();

        while (true) {
            printMessage();
            Command command = readCommand();
            if (command.getAction() == Action.EXIT) {
                return;
            } else if (command.getAction() == Action.ERROR) {
                continue;
            } else {
                try{
                    STUDENT_COMMAND_HANDLER.processCommand(command);
                } catch (WrongDataFormatException e) {
                    System.out.println(e.getLocalizedMessage());
                    continue;
                }
            }
        }
    }

    private static void prepareSomeStudentsForStatsTest() throws WrongDataFormatException {
        String[] preDefinedStudents = {
                "Дубинский,Иван,Java,Москва,22",
                "Журов,Петр,DevOps,Краснодар,25",
                "Гаврилова,Ирина,Manager,Краснодар,23",
                "Иванова,Светлана,Java,Москва,32",
                "Зубов,Павел,DevOps,Москва,32",
                "Васильев,Константин,Java,Сочи,35",
                "Абрамова,Галина,Java,Краснодар,30",
                "Емельянов,Олег,DevOps,Москва,30",
                "Бубликов,Дмитрий,Manager,Сочи,39",
                "Краснова,Дарья,Java,Белгород,22"
        };
        // Москва - 4, Краснодар - 3, Сочи - 2, Белгород - 1
        // Java - 5, DevOps - 3, Manager - 2
        // Абрамова, Бубликов, Васильев, Гаврилова, Дубинский, Емельянов, Журов, Зубов, Иванова, Краснова
        for (String data : preDefinedStudents) {
            STUDENT_COMMAND_HANDLER.processCommand(new Command(CREATE, data));
        }
    }

    private static void printMessage() {
        System.out.println("----------------------");
        System.out.println("0. Выход");
        System.out.println("1. Создание данных");
        System.out.println("2. Обновление данных");
        System.out.println("3. Удаление данных");
        System.out.println("4. Вывод статистики по курсам");
        System.out.println("5. Вывод статистики по городам");
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
