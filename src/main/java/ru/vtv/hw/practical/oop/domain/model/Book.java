package ru.vtv.hw.practical.oop.domain.model;

import lombok.Builder;
import lombok.Getter;

import static java.lang.String.format;

@Builder
@Getter
public class Book {
    private Integer id;
    private String title;
    private Author author;
    private Category category;

    @Override
    public String toString() {
        return format("[%d] %s, %s (%s)", id, title, author.getName(), category.getName());
    }
}
