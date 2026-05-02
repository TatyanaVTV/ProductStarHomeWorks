package ru.vtv.hw.practical.csvstudents.dao.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EducationLevel {
    ASSOCIATE("associate's degree"),
    BACHELOR("bachelor's degree"),
    HIGH_SCHOOL("high school"),
    MASTER("master's degree"),
    COLLEGE("some college"),
    SOME_HIGH_SCHOOL("some high school");

    public final String value;
}
