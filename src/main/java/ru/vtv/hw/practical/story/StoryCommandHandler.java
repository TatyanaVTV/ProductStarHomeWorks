package ru.vtv.hw.practical.story;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.String.format;

@Slf4j
public class StoryCommandHandler {
    private static final Story STORY = new Story();
    private static final List<String> GENERATED_STORIES = new ArrayList<>();

    public void processCommand(Command command) throws StoryElementException {
        var action = command.getAction();

        try {
            switch (action) {
                case CREATE_CHARACTER -> processCreateCharacterCommand(command);
                case CREATE_ACTION -> processCreateActionCommand(command);
                case CREATE_PLACE -> processCreatePlaceCommand(command);
                case GENERATE_STORY -> processGenerateStoryCommand();
                case SHOW_STORY_DATA -> processShowStoryDataCommand();
                case SHOW_GENERATED_STORIES -> processShowGeneratedStoriesCommand();
                default -> System.out.printf("Действие %s не поддерживается.\n", action);
            }
        } finally {
            log.debug("Обработка команды. Действие: {}, данные: {}", command.getAction().name(), command.getData());
        }
    }

    public static void printCurrentStoryState() {
        System.out.printf("У нас есть персонажи (%s шт.), действия (%s шт.), места (%s шт.).%n",
                STORY.getCharacters().size(), STORY.getActions().size(), STORY.getPlaces().size());
    }

    private void processCreateCharacterCommand(Command command) {
        var characterName = command.getData();
        STORY.addCharacter(characterName);
    }

    private void processCreateActionCommand(Command command) {
        var actionName = command.getData();
        STORY.addAction(actionName);
    }

    private void processCreatePlaceCommand(Command command) {
        var placeName = command.getData();
        STORY.addPlace(placeName);
    }

    private void processGenerateStoryCommand() {
        var random = new Random();

        var characters = new ArrayList<>(STORY.getCharacters());
        var actions = new ArrayList<>(STORY.getActions());
        var places = new ArrayList<>(STORY.getPlaces());

        var character = characters.get(random.nextInt(characters.size()));
        var action = actions.get(random.nextInt(actions.size()));
        var place = places.get(random.nextInt(places.size()));

        var generatedStory = format("%s %s %s.", character, action, place);
        GENERATED_STORIES.add(generatedStory);

        System.out.println("Сгенерированная история: " + generatedStory);
    }

    private void processShowStoryDataCommand() {
        printCurrentStoryState();
        STORY.printData();
    }

    private void processShowGeneratedStoriesCommand() {
        System.out.println("Сгенерированные ранее истории: ");
        GENERATED_STORIES.forEach(story -> System.out.println("*** " + story));

        if (GENERATED_STORIES.isEmpty()) System.out.println("*** Истории ранее не генерировались");
    }
}
