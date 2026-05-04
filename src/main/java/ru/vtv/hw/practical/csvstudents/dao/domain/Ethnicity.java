package ru.vtv.hw.practical.csvstudents.dao.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Ethnicity {
    GROUP_A("group A"),
    GROUP_B("group B"),
    GROUP_C("group C"),
    GROUP_D("group D"),
    GROUP_E("group E");

    private final String value;
}
