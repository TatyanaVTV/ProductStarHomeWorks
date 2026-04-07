package ru.vtv.hw.practical.story;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StoryTest {
    private static final String GNOME = "Гном";
    private static final String PRINCESS = "Принцесса";

    private static final String DANCE = "танцует";
    private static final String FLY = "летает";

    private static final String FORREST = "в лесу";
    private static final String SKY = "в небе";

    @Test
    void testAddCharacter() {
        var story = new Story();
        story.addCharacter(GNOME);
        assertTrue(story.getCharacters().contains(GNOME), "Персонаж должен был быть добавлен!");
    }

    @Test
    void testAddAction() {
        var story = new Story();
        story.addAction(DANCE);
        assertTrue(story.getActions().contains(DANCE), "Действие должно было быть добавлено!");
    }

    @Test
    void testAddPlace() {
        var story = new Story();
        story.addPlace(FORREST);
        assertTrue(story.getPlaces().contains(FORREST), "Место должно было быть добавлено!");
    }

    @Test
    void testDuplicateCharacterThrowsException() {
        var story = new Story();
        story.addCharacter(GNOME);

        var expectedMsg = "Story already has element '" + GNOME + "'!";
        var exception = assertThrows(StoryElementException.class, () -> story.addCharacter(GNOME));
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    void testNullCharacterThrowsException() {
        var story = new Story();
        var expectedMsg = "Invalid name for element: 'null'!";
        var exception = assertThrows(StoryElementException.class, () -> story.addCharacter(null));
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    void testBlankCharacterThrowsException() {
        var story = new Story();
        var expectedMsg = "Invalid name for element: '   '!";
        var exception = assertThrows(StoryElementException.class, () -> story.addCharacter("   "));
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    void testCharacterFormatting() {
        var story = new Story();
        story.addCharacter("прИвЕт");
        assertTrue(story.getCharacters().contains("Привет"));
    }

    @Test
    void testActionFormatting() {
        var story = new Story();
        story.addAction("ЛЕТАЕТ");
        assertTrue(story.getActions().contains("летает"));
    }

    @Test
    void testPlaceFormatting() {
        var story = new Story();
        story.addPlace("НА КУХНЕ");
        assertTrue(story.getPlaces().contains("на кухне"));
    }

    @Test
    void testGetCharactersReturnsCopy() {
        var story = new Story();
        story.addCharacter(GNOME);

        var characters = story.getCharacters();

        assertThrows(UnsupportedOperationException.class, () -> characters.add(PRINCESS));
        assertEquals(1, story.getCharacters().size()); // оригинал не изменился
    }

    @Test
    void testPrintDataDoesNotThrow() {
        var story = new Story();
        story.addCharacter(GNOME);
        story.addAction(DANCE);
        story.addPlace(FORREST);

        assertDoesNotThrow(story::printData);
    }

    @Test
    void testMultipleElements() {
        var story = new Story();
        story.addCharacter(GNOME);
        story.addCharacter(PRINCESS);
        story.addAction(DANCE);
        story.addAction(FLY);
        story.addPlace(FORREST);
        story.addPlace(SKY);

        assertEquals(2, story.getCharacters().size());
        assertEquals(2, story.getActions().size());
        assertEquals(2, story.getPlaces().size());
    }
}
