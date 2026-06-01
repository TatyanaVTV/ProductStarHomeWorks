package ru.vtv.hw.practical.oop.cli.service;

import ru.vtv.hw.practical.oop.domain.model.Category;

import java.util.List;

public interface CategoryService {
    Category add(String name);
    void remove(Category category);
    List<Category> getAll();
    List<Category> getByName(String title);
}
