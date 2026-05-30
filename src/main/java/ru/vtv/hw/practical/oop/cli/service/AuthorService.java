package ru.vtv.hw.practical.oop.cli.service;

import ru.vtv.hw.practical.oop.domain.model.Author;

import java.util.List;

public interface AuthorService {
    Author add(String name);
    void remove(Author author);
    List<Author> getAll();
    List<Author> getByName(String name);
}
