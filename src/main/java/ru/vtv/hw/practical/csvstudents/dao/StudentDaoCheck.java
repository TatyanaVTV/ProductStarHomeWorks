package ru.vtv.hw.practical.csvstudents.dao;

import ru.vtv.hw.practical.csvstudents.dao.domain.Student;

import java.util.List;

import static ru.vtv.hw.practical.csvstudents.dao.domain.Ethnicity.GROUP_A;
import static ru.vtv.hw.practical.csvstudents.dao.domain.Gender.FEMALE;
import static ru.vtv.hw.practical.csvstudents.dao.domain.TestPreparationCourse.COMPLETED;

public class StudentDaoCheck {

    public static void main(String[] args) {
        var studentDao = new StudentDaoImpl();

        // 1. Получить всех студентов
        System.out.println("=== Все студенты ===");
        var allStudents = studentDao.findAll();
        printStudents(allStudents);

        // 2. Студенты, прошедшие подготовительный курс
        System.out.println("\n=== Студенты с завершённым подготовительным курсом ===");
        var completedCourse = studentDao.findByTestPreparationCourse(COMPLETED);
        printStudents(completedCourse);

        // 3. Студенты с баллом по математике выше 80
        System.out.println("\n=== Студенты с баллом по математике > 90 ===");
        var highMathScores = studentDao.findByMathScoreGreaterThan(90);
        printStudents(highMathScores);

        // 4. Студенты мужского пола из группы A
        System.out.println("\n=== Женщины из группы A ===");
        var maleGroupA = studentDao.findByGenderAndRace(FEMALE, GROUP_A);
        printStudents(maleGroupA);
    }

    private static void printStudents(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("Нет данных");
            return;
        }

        students.forEach(student ->
                System.out.printf("ID: %d, Пол: %s, Группа: %s, Математика: %d%n",
                        student.getId(),
                        student.getGender().name().toLowerCase(),
                        student.getRaceEthnicity().getValue(),
                        student.getMathScore())
        );
        System.out.println("Всего: " + students.size() + " записей");
    }
}
