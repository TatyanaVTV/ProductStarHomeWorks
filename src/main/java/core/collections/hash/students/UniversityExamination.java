package core.collections.hash.students;

import core.collections.hash.students.exceptions.ScoreNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class UniversityExamination implements Examination {
    private final ExcellentMarksCache<String, Score> cache = new ExcellentMarksCache<>(5);

    public static final int INITIAL_CAPACITY = 256;

    private final Map<String, Set<Score>> scores = new HashMap<>(INITIAL_CAPACITY);

    private int averageMarksCalls = 0;

    public int getAverageMarksCalls() {
        return averageMarksCalls;
    }

    @Override
    public void addScore(Score score) {
        Set<Score> studentScores = scores.get(score.name());
        if (studentScores == null) {
            studentScores = new HashSet<>();
        }
        studentScores.add(score);
        if (score.score() == 5) cache.put(score.name(), score);
        scores.put(score.name(), studentScores);
    }

    @Override
    public void addMultipleScores(List<Score> scores) {
        for (Score score : scores) {
            addScore(score);
        }
    }

    @Override
    public Score getScore(String name, String subject) throws ScoreNotFoundException {
        Set<Score> studentScores = scores.get(name);
        Score score = studentScores.stream().filter(record -> record.subject().equals(subject)).findFirst().orElse(null);
        if (score == null) {
            throw new ScoreNotFoundException("Score for student " + name + " for subject '" + subject + "' not found!");
        }
        return score;
    }

    @Override
    public double getAverageForSubject(String subject) {
        averageMarksCalls++;

        double averageMark = getAllScores().stream()
                .filter(score -> score.subject().equals(subject))
                .mapToInt(Score::score)
                .average()
                .orElse(0.00);
        System.out.printf("\nAverage mark for subject '%s': %f\n", subject, averageMark);
        return averageMark;
    }

    @Override
    public Set<String> multipleSubmissionsStudentNames() {
        Set<String> students = scores.entrySet().stream()
                .filter(studentScores -> studentScores.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        System.out.println("\n======= Multiple Submissions Students Names ======");
        System.out.println(students);

        return students;
    }

    @Override
    public Set<String> lastFiveStudentsWithExcellentMarkOnAnySubject() {
        System.out.println("\n===== Last five students with excellent mark =====");
        cache.keySet().forEach(System.out::println);
        return cache.keySet();
    }

    @Override
    public Collection<Score> getAllScores() {
        Set<Score> allScores = new HashSet<>();

        System.out.println("\n=================== All scores ===================");
        for (Map.Entry<String, Set<Score>> studentScores : scores.entrySet()) {
            allScores.addAll(studentScores.getValue());
            System.out.println(studentScores.getValue());
        }
        return allScores;
    }
}

class ExcellentMarksCache<KEY, VALUE> extends LinkedHashMap<KEY, VALUE> {
    private final int capacity;

    public ExcellentMarksCache(int capacity) {
        super(capacity, 0.75f, false);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<KEY, VALUE> eldest) {
        return size() > capacity;
    }
}
