package ru.vtv.hw.practical.csvstudents.dao.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Student {
    private int id;
    private Gender gender;
    private Ethnicity raceEthnicity;
    private EducationLevel parentalLevelOfEducation;
    private Lunch lunch;
    private TestPreparationCourse testPreparationCourse;
    private int mathScore;
    private int readingScore;
    private int writingScore;
}
