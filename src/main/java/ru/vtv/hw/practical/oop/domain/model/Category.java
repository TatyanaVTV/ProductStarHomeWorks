package ru.vtv.hw.practical.oop.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Category {
    private Integer id;
    private String name;
}
