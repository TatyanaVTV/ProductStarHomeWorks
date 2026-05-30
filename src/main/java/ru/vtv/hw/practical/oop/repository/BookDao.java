package ru.vtv.hw.practical.oop.repository;

import ru.vtv.hw.practical.oop.domain.model.Author;
import ru.vtv.hw.practical.oop.domain.model.Book;
import ru.vtv.hw.practical.oop.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    List<Book> getAll();
    Optional<Book> find(int id);
    void save(Book book);
    Book create(String title, Author author, Category category);
    void delete(int id);
}
