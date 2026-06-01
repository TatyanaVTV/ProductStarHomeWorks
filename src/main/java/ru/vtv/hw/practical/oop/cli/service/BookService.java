package ru.vtv.hw.practical.oop.cli.service;

import ru.vtv.hw.practical.oop.domain.model.Author;
import ru.vtv.hw.practical.oop.domain.model.Book;
import ru.vtv.hw.practical.oop.domain.model.Category;

import java.util.List;

public interface BookService {
    Book add(String title, Author author, Category category);
    void remove(Book book);
    List<Book> getAll();
    List<Book> getByTitle(String title);
    List<Book> getByAuthor(Author author);
    List<Book> getByCategory(Category category);
}
