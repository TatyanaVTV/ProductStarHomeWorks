package ru.vtv.hw.practical.story;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;
import static ru.vtv.hw.practical.story.StoryElementException.alreadyExists;
import static ru.vtv.hw.practical.story.StoryElementException.invalidName;

public class Story {
    private final Set<String> characters = new HashSet<>();
    private final Set<String> actions = new HashSet<>();
    private final Set<String> places = new HashSet<>();

    public void addCharacter(String character) {
        var formatedCharacter = baseCheckElement(characters, character, ElementType.CHARACTER);
        characters.add(formatedCharacter);
    }

    public void addAction(String action) {
        var formatedAction = baseCheckElement(actions, action, ElementType.ACTION);
        actions.add(formatedAction);
    }

    public void addPlace(String place) {
        var formatedPlace = baseCheckElement(places, place, ElementType.PLACE);
        places.add(formatedPlace);
    }

    public Set<String> getCharacters() {
        return Set.copyOf(characters);
    }

    public Set<String> getActions() {
        return Set.copyOf(actions);
    }

    public Set<String> getPlaces() {
        return Set.copyOf(places);
    }

    public void printData() {
        System.out.print("*** Персонажи: ");
        System.out.println(String.join(", ", characters));

        System.out.print("*** Действия: ");
        System.out.println(String.join(", ", actions));

        System.out.print("*** Места: ");
        System.out.println(String.join(", ", places));
    }

    private String baseCheckElement(Set<String> elementsMap, String elementName, ElementType elementType) {
        if (elementsMap.contains(elementName)) throw alreadyExists(elementName);
        if (isNull(elementName) || elementName.isBlank()) throw invalidName(elementName);

        return switch (elementType) {
            case CHARACTER -> elementName.substring(0, 1).toUpperCase()
                    + elementName.substring(1).toLowerCase();
            case ACTION, PLACE -> elementName.toLowerCase();
        };
    }

    private enum ElementType {
        CHARACTER,
        ACTION,
        PLACE
    }
}
