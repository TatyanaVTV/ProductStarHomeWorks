package ru.vtv.hw.practical.oop.repository.inmemory;

import ru.vtv.hw.practical.oop.domain.model.Author;
import ru.vtv.hw.practical.oop.domain.model.Book;
import ru.vtv.hw.practical.oop.domain.model.Category;
import ru.vtv.hw.practical.oop.repository.BookDao;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Objects.isNull;
import static ru.vtv.hw.practical.oop.domain.WrongDataException.saveDataWithoutId;

public class BookDaoImpl implements BookDao {
    private final AtomicInteger counter = new AtomicInteger();
    private final Map<Integer, Book> data = new ConcurrentHashMap<>();

    @Override
    public List<Book> getAll() {
        return List.copyOf(data.values());
    }

    @Override
    public Optional<Book> find(int id) {
        var foundRecord = data.get(id);
        return isNull(foundRecord) ? Optional.empty() : Optional.of(foundRecord);
    }

    @Override
    public void save(Book book) {
        if (isNull(book.getId())) throw saveDataWithoutId();
        data.put(book.getId(), book);
    }

    @Override
    public Book create(String title, Author author, Category category) {
        var book = Book.builder()
                .id(counter.incrementAndGet())
                .title(title)
                .author(author)
                .category(category)
                .build();
        save(book);
        return book;
    }

    @Override
    public void delete(int id) {
        data.remove(id);
    }
}
