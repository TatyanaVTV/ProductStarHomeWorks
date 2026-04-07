package ru.vtv.hw.practical.detective;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Scanner;

import static ru.vtv.hw.practical.detective.Action.*;

@Slf4j
public class DetectiveCaseCheck {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final DetectiveCaseCommandHandler DETECTIVE_CASE_HANDLER = new DetectiveCaseCommandHandler();

    public static void main(String[] args) {
        detectiveCaseSetup();

        while (true) {
            printMenu();
            var command = readCommand();
            if (EXIT.equals(command.getAction())) {
                System.out.println("До новых встречи!");
                return;
            } else {
                try {
                    DETECTIVE_CASE_HANDLER.processCommand(command);
                } catch (EvidenceException e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("----------------------");
        System.out.println("Добро пожаловать в Детективную игру!");
        System.out.println("Выберите действие:");

        System.out.println(ADD_EVIDENCE.getCode() + ". Добавить улику");
        System.out.println(CHECK_EVIDENCE.getCode() + ". Проверить наличие улики");
        System.out.println(REMOVE_EVIDENCE.getCode() + ". Удалить улику");
        System.out.println(COMPARE_EVIDENCE.getCode() + ". Сравнить с базой данных");
        System.out.println(SHOW_EVIDENCES.getCode() + ". Показать все найденные улики");
        System.out.println(EXIT.getCode() + ". Выход");

        System.out.println("----------------------");
    }

    private static Command readCommand() {
        try {
            var code = SCANNER.nextLine();
            var actionCode = Integer.valueOf(code);
            var action = Action.fromCode(actionCode);

            if (action.isRequireAdditionalData()) {
                System.out.print("Введите значение: ");
                var data = SCANNER.nextLine();
                return new Command(action, data);
            } else {
                return new Command(action);
            }
        } catch (Exception ex) {
            System.out.printf("Проблема обработки ввода. %s\n", ex.getLocalizedMessage());
        }
        return new Command(ERROR);
    }

    private static void detectiveCaseSetup() {
        var preDefinedCEvidences = List.of(
                "Отпечаток пальца на двери",
                "Отпечаток пальца на стене",
                "ДНК преступника",
                "Волосы на кресле",
                "След от ботинка"
        );

        preDefinedCEvidences.forEach(evidence ->
                DETECTIVE_CASE_HANDLER.processCommand(new Command(ADD_EVIDENCE, evidence)));
    }
}
