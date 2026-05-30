package ru.vtv.hw.practical.oop.repository.inmemory;

import ru.vtv.hw.practical.oop.domain.model.Category;
import ru.vtv.hw.practical.oop.repository.CategoryDao;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Objects.isNull;
import static ru.vtv.hw.practical.oop.domain.WrongDataException.saveDataWithoutId;

public class CategoryDaoImpl implements CategoryDao {
    private final AtomicInteger counter = new AtomicInteger();
    private final Map<Integer, Category> data = new ConcurrentHashMap<>();

    @Override
    public List<Category> getAll() {
        return List.copyOf(this.data.values());
    }

    @Override
    public Optional<Category> find(int id) {
        var foundRecord = data.get(id);
        return isNull(foundRecord) ? Optional.empty() : Optional.of(foundRecord);
    }

    @Override
    public void save(Category category) {
        if (isNull(category.getId())) throw saveDataWithoutId();
        data.put(category.getId(), category);
    }

    @Override
    public Category create(String name) {
        var category = Category.builder()
                .id(counter.incrementAndGet())
                .name(name)
                .build();
        save(category);
        return category;
    }

    @Override
    public void delete(int id) {
        data.remove(id);
    }
}
