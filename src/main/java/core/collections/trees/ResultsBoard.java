package core.collections.trees;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class ResultsBoard {
    private final TreeSet<Student> studentsScores;

    public ResultsBoard() {
        studentsScores = new TreeSet<>();
    }

    void addStudent(String name, Float score) {
        studentsScores.add(new Student(name, score));
    }

    List<String> top3 () {
        List<String> topThreeStudents = new ArrayList<>();
        int count = 0;
        for (Student student : studentsScores.descendingSet()) {
            if (count++ < 3) {
                topThreeStudents.add(student.name());
            }
        }
        return topThreeStudents;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Student student : studentsScores) {
            sb.append(student);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}

record Student(String name, Float score) implements Comparable<Student> {

    @Override
    public int compareTo(@NotNull Student o) {
        return Float.compare(score, o.score);
    }

    @Override
    public String toString() {
        return name + " : " + score;
    }
}