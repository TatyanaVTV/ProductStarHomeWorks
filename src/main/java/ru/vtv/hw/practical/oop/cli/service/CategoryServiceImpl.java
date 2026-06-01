package ru.vtv.hw.practical.oop.cli.service;

import ru.vtv.hw.practical.oop.cli.Injector;
import ru.vtv.hw.practical.oop.domain.model.Category;
import ru.vtv.hw.practical.oop.repository.CategoryDao;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao;

    public CategoryServiceImpl() {
        this.categoryDao = Injector.getInstance().getService(CategoryDao.class);
    }

    public Category add(String name) {
        return categoryDao.create(name);
    }

    public void remove(Category category) {
        categoryDao.delete(category.getId());
    }

    public List<Category> getAll() {
        return categoryDao.getAll();
    }

    public List<Category> getByName(String name) {
        return getAll().stream()
                .filter(category -> category.getName().equals(name))
                .toList();
    }
}
