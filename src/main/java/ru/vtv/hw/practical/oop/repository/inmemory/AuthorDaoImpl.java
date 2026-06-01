package ru.vtv.hw.practical.oop.repository.inmemory;

import ru.vtv.hw.practical.oop.domain.model.Author;
import ru.vtv.hw.practical.oop.repository.AuthorDao;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Objects.isNull;
import static ru.vtv.hw.practical.oop.domain.WrongDataException.saveDataWithoutId;

public class AuthorDaoImpl implements AuthorDao {
    private final AtomicInteger counter = new AtomicInteger();
    private final Map<Integer, Author> data = new ConcurrentHashMap<>();

    @Override
    public List<Author> getAll() {
        return List.copyOf(data.values());
    }

    @Override
    public Optional<Author> find(int id) {
        var foundRecord = data.get(id);
        return isNull(foundRecord) ? Optional.empty() : Optional.of(foundRecord);
    }

    @Override
    public void save(Author author) {
        if (isNull(author.getId())) throw saveDataWithoutId();
        data.put(author.getId(), author);
    }

    @Override
    public Author create(String name) {
        var author = Author.builder()
                .id(counter.incrementAndGet())
                .name(name)
                .build();
        save(author);
        return author;
    }

    @Override
    public void delete(int id) {
        data.remove(id);
    }
}
