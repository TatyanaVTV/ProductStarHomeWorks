package ru.vtv.hw.practical.story;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Scanner;

import static ru.vtv.hw.practical.story.Action.*;

@Slf4j
public class StoryCheck {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final StoryCommandHandler STORY_COMMAND_HANDLER = new StoryCommandHandler();

    public static void main(String[] args) {
        storySetup();

        while (true) {
            printMenu();
            var command = readCommand();
            if (EXIT.equals(command.getAction())) {
                System.out.println("До новых встречи!");
                return;
            } else {
                try {
                    STORY_COMMAND_HANDLER.processCommand(command);
                } catch (StoryElementException e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
        }
    }

    private static void storySetup() {
        var preDefinedCharacters = List.of("Гном", "Принцесса", "Робот");
        var preDefinedActions = List.of("танцует", "летает", "сражается");
        var preDefinedPlaces = List.of("в лесу", "на кухне", "в космосе");

        preDefinedCharacters.forEach(character ->
                STORY_COMMAND_HANDLER.processCommand(new Command(CREATE_CHARACTER, character)));
        preDefinedActions.forEach(action ->
                STORY_COMMAND_HANDLER.processCommand(new Command(CREATE_ACTION, action)));
        preDefinedPlaces.forEach(place ->
                STORY_COMMAND_HANDLER.processCommand(new Command(CREATE_PLACE, place)));

        STORY_COMMAND_HANDLER.processCommand(new Command(SHOW_STORY_DATA));
    }

    private static void printMenu() {
        System.out.println("----------------------");
        System.out.println("Добро пожаловать в Генератор случайных историй!");
        StoryCommandHandler.printCurrentStoryState();

        System.out.println(CREATE_CHARACTER.getCode() + ". Добавить персонажа");
        System.out.println(CREATE_ACTION.getCode() + ". Добавить действие");
        System.out.println(CREATE_PLACE.getCode() + ". Добавить место");
        System.out.println(GENERATE_STORY.getCode() + ". Сгенерировать историю");
        System.out.println(SHOW_STORY_DATA.getCode() + ". Показать данные");
        System.out.println(SHOW_GENERATED_STORIES.getCode() + ". Показать сгенерированные истории");
        System.out.println(EXIT.getCode() + ". Выход");

        System.out.println("----------------------");
    }

    private static Command readCommand() {
        try {
            var code = SCANNER.nextLine();
            var actionCode = Integer.valueOf(code);
            var action = Action.fromCode(actionCode);

            if (action.isRequireAdditionalData()) {
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
}
