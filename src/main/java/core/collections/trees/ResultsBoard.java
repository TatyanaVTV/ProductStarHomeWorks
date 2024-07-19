package core.collections.trees;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public boolean equals(Object obj) {
        if (obj instanceof Student) {
            return name.equals(((Student) obj).name());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, score) ;
    }

    @Override
    public int compareTo(@NotNull Student o) {
        if (score.compareTo(o.score) == 0) {
            return name.compareTo(o.name);
        } else {
            return Float.compare(score, o.score);
        }
    }

    @Override
    public String toString() {
        return name + " : " + score;
    }
}