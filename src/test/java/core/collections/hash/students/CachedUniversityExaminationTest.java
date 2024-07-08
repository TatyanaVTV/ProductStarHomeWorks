package core.collections.hash.students;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static core.collections.hash.students.TestData.*;

class CachedUniversityExaminationTest {
    private final CachedUniversityExamination cachedUniversity = new CachedUniversityExamination(new UniversityExamination());

    @BeforeEach
    void setUp() {
        cachedUniversity.addMultipleScores(List.of(
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
        //getAverageForSubject
    void callOnceForRepeatingRequests() {
        String request = SBJ_HISTORY;

        Double averageBySubject = cachedUniversity.getAverageForSubject(request);

        Assertions.assertEquals(4, averageBySubject);

        cachedUniversity.getAverageForSubject(request);
        cachedUniversity.getAverageForSubject(request);
        cachedUniversity.getAverageForSubject(request);

        Assertions.assertEquals(1, cachedUniversity.getAverageMarksCalls());
    }

    @Test
    void callManyTimesForRepeatingRequests() {
        cachedUniversity.getAverageForSubject(SBJ_HISTORY);
        cachedUniversity.getAverageForSubject(SBJ_PHYSICS);
        cachedUniversity.getAverageForSubject(SBJ_HISTORY);

        Assertions.assertEquals(2, cachedUniversity.getAverageMarksCalls());

        cachedUniversity.getAverageForSubject(SBJ_FOREIGN_LANGUAGE);
        Assertions.assertEquals(3, cachedUniversity.getAverageMarksCalls()); // т.к. ранее SBJ_FOREIGN_LANGUAGE не вызывался

        cachedUniversity.getAverageForSubject(SBJ_HISTORY);
        Assertions.assertEquals(3, cachedUniversity.getAverageMarksCalls());  // т.к. ранее SBJ_HISTORY уже вызывался и ещё есть в кэше

        cachedUniversity.getAverageForSubject(SBJ_PHYSICS);
        Assertions.assertEquals(4, cachedUniversity.getAverageMarksCalls());  // т.к. ранее SBJ_PHYSICS уже вызывался, но из кэша уже удален
    }
}
