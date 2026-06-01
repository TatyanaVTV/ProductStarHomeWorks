package ru.vtv.hw.practical.oop.repository;

import ru.vtv.hw.practical.oop.domain.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    List<Author> getAll();
    Optional<Author> find(int id);
    void save(Author author);
    Author create(String name);
    void delete(int id);
}
