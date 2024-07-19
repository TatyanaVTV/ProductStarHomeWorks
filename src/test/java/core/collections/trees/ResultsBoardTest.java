package core.collections.trees;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ResultsBoardTest {
    @Test
    void top3() {
        ResultsBoard resultsBoard = new ResultsBoard();

        resultsBoard.addStudent("Olesja Morozova", 4.25f);
        resultsBoard.addStudent("Semen Filatov", 4.97f);
        resultsBoard.addStudent("Ivan Petrov", 4.56f);
        resultsBoard.addStudent("Irina Vetrova", 4.88f);
        resultsBoard.addStudent("Igor Andreev", 3.58f);

        List<String> expectedList = List.of("Semen Filatov", "Irina Vetrova", "Ivan Petrov");
        List<String> topThreeStudents = resultsBoard.top3();

        System.out.println("===== Results Board ======");
        System.out.println(resultsBoard);

        System.out.println("===== Top 3 Students =====");
        System.out.println(topThreeStudents);

        Assertions.assertEquals(expectedList, resultsBoard.top3());
    }
}