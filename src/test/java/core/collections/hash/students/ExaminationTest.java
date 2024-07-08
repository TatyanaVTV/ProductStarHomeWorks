package core.collections.hash.students;

import core.collections.hash.students.exceptions.ScoreNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import static core.collections.hash.students.TestData.*;

public class ExaminationTest {
    private final Examination university = new UniversityExamination();
    @BeforeEach
    void setUp() {
        university.addMultipleScores(List.of(
                new Score(ST_IVAN_PETROV, SBJ_FOREIGN_LANGUAGE, 4),
                new Score(ST_ANNA_VASILIEVA, SBJ_PHYSICS, 5),
                new Score(ST_ILYA_FEDOROV, SBJ_PHYSICS, 5),
                new Score(ST_ANTON_FILLIPOV, SBJ_HISTORY, 3),
                new Score(ST_IRINA_NIKOLAEVA, SBJ_HISTORY, 5),
                new Score(ST_PETR_POPOV, SBJ_PHYSICS, 4),
                new Score(ST_IRINA_NIKOLAEVA, SBJ_FOREIGN_LANGUAGE, 5),
                new Score(ST_ANNA_VASILIEVA, SBJ_FOREIGN_LANGUAGE, 4),
                new Score(ST_VASILY_MOROZOV, SBJ_FOREIGN_LANGUAGE, 5),
                new Score(ST_OLGA_GAVRILOVA, SBJ_PHYSICS, 5),
                new Score(ST_VICTORIA_IONOVA, SBJ_FOREIGN_LANGUAGE, 5)
        ));
    }

    @Test
    void addNewScore() throws ScoreNotFoundException {
        Assertions.assertThrows(ScoreNotFoundException.class, () -> university.getScore(ST_IRINA_NIKOLAEVA, SBJ_PHYSICS));

        university.addScore(new Score(ST_IRINA_NIKOLAEVA, SBJ_PHYSICS, 5));
        Assertions.assertEquals(5, university.getScore(ST_IRINA_NIKOLAEVA, SBJ_PHYSICS).score());
    }

    @Test
    void addScoreForAlreadyPassedExam() throws ScoreNotFoundException {
        Assertions.assertEquals(4, university.getScore(ST_IVAN_PETROV, SBJ_FOREIGN_LANGUAGE).score());
        university.addScore(new Score(ST_IVAN_PETROV, SBJ_FOREIGN_LANGUAGE, 5));
        Assertions.assertEquals(5, university.getScore(ST_IVAN_PETROV, SBJ_FOREIGN_LANGUAGE).score());
    }

    @Test
    void getExistingScore() throws ScoreNotFoundException {
        Assertions.assertEquals(4, university.getScore(ST_IVAN_PETROV, SBJ_FOREIGN_LANGUAGE).score());
    }

    @Test
    void getNotExistingScore() {
        Assertions.assertThrows(ScoreNotFoundException.class, () -> university.getScore(ST_IRINA_NIKOLAEVA, SBJ_PHYSICS));
    }

    @Test
    void getAverageForExistingSubject() {
        Assertions.assertEquals(4.00, university.getAverageForSubject(SBJ_HISTORY));
    }

    @Test
    void getAverageForNotExistingSubject() {
        Assertions.assertEquals(0.00, university.getAverageForSubject("Not Existing Subject"));
    }

    @Test
    void multipleSubmissionsStudentNames() {
        Set<String> expectedStudentNames = Set.of(ST_ANNA_VASILIEVA, ST_IRINA_NIKOLAEVA);
        Assertions.assertEquals(expectedStudentNames, university.multipleSubmissionsStudentNames());
    }

    @Test
    void lastFiveStudentsWithExcellentMarkOnAnySubject() {
        Set<String> expectedStudents =
                Set.of(ST_ILYA_FEDOROV, ST_IRINA_NIKOLAEVA, ST_VASILY_MOROZOV, ST_OLGA_GAVRILOVA, ST_VICTORIA_IONOVA);
        Set<String> lastFiveStudents = university.lastFiveStudentsWithExcellentMarkOnAnySubject();
        Assertions.assertFalse(lastFiveStudents.contains(ST_ANNA_VASILIEVA));
        Assertions.assertEquals(expectedStudents, lastFiveStudents);

        university.addScore(new Score(ST_IVAN_PETROV, SBJ_PHYSICS, 5));

        expectedStudents =
                Set.of(ST_IRINA_NIKOLAEVA, ST_VASILY_MOROZOV, ST_OLGA_GAVRILOVA, ST_VICTORIA_IONOVA, ST_IVAN_PETROV);
        lastFiveStudents = university.lastFiveStudentsWithExcellentMarkOnAnySubject();
        Assertions.assertFalse(lastFiveStudents.contains(ST_ILYA_FEDOROV));
        Assertions.assertEquals(expectedStudents, lastFiveStudents);
    }

    @Test
    void getAllScores() {
        Collection<Score> allScores = university.getAllScores();
        Assertions.assertEquals(11, allScores.size());
    }
}
