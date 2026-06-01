package ru.vtv.hw.practical.oop.repository;

import ru.vtv.hw.practical.oop.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {
    List<Category> getAll();
    Optional<Category> find(int id);
    void save(Category category);
    Category create(String name);
    void delete(int id);
}
