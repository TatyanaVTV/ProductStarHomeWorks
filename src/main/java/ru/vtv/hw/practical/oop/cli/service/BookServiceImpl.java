package ru.vtv.hw.practical.oop.cli.service;

import ru.vtv.hw.practical.oop.cli.Injector;
import ru.vtv.hw.practical.oop.domain.model.Author;
import ru.vtv.hw.practical.oop.domain.model.Book;
import ru.vtv.hw.practical.oop.domain.model.Category;
import ru.vtv.hw.practical.oop.repository.BookDao;

import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    public BookServiceImpl() {
        this.bookDao = Injector.getInstance().getService(BookDao.class);
    }

    public Book add(String title, Author author, Category category) {
        return bookDao.create(title, author, category);
    }

    public void remove(Book book) {
        bookDao.delete(book.getId());
    }

    public List<Book> getAll() {
        return bookDao.getAll();
    }

    public List<Book> getByTitle(String title) {
        return getAll().stream()
                .filter(book -> book.getTitle().equals(title))
                .toList();
    }

    public List<Book> getByAuthor(Author author) {
        return getAll().stream()
                .filter(book -> book.getAuthor().equals(author))
                .toList();
    }

    public List<Book> getByCategory(Category category) {
        return getAll().stream()
                .filter(book -> book.getCategory().equals(category))
                .toList();
    }
}
