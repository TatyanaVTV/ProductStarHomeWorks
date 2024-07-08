package core.collections.hash.students;

import core.collections.hash.students.exceptions.ScoreNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface Examination {
    void addScore(Score score);
    void  addMultipleScores(List<Score> scores);

    Score getScore(String name, String subject) throws ScoreNotFoundException;

    double getAverageForSubject(String subject);

    Set<String> multipleSubmissionsStudentNames();

    Set<String> lastFiveStudentsWithExcellentMarkOnAnySubject();

    Collection<Score> getAllScores();
}
