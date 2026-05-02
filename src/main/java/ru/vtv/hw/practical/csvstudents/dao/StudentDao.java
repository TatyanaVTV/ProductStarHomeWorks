package ru.vtv.hw.practical.csvstudents.dao;

import ru.vtv.hw.practical.csvstudents.dao.domain.Ethnicity;
import ru.vtv.hw.practical.csvstudents.dao.domain.Gender;
import ru.vtv.hw.practical.csvstudents.dao.domain.Student;
import ru.vtv.hw.practical.csvstudents.dao.domain.TestPreparationCourse;

import java.util.List;

public interface StudentDao {
    List<Student> findAll();
    List<Student> findByTestPreparationCourse(TestPreparationCourse courseStatus);
    List<Student> findByMathScoreGreaterThan(int minScore);
    List<Student> findByGenderAndRace(Gender gender, Ethnicity raceEthnicity);
}
