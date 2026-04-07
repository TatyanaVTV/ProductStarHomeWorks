package ru.vtv.hw.practical.story;

public class StoryElementException extends RuntimeException {
    private StoryElementException(String message) {
        super(message);
    }

    public static StoryElementException invalidName(String elementName) {
        return new StoryElementException("Invalid name for element: '" + elementName + "'!");
    }

    public static StoryElementException alreadyExists(String elementName) {
        return new StoryElementException("Story already has element '" + elementName + "'!");
    }
}
