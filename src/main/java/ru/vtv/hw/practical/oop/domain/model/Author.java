package ru.vtv.hw.practical.oop.domain.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Author {
    private Integer id;
    private String name;
}
