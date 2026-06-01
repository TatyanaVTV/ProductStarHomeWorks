package ru.vtv.hw.practical.oop.cli.service;

import ru.vtv.hw.practical.oop.cli.Injector;
import ru.vtv.hw.practical.oop.domain.model.Author;
import ru.vtv.hw.practical.oop.repository.AuthorDao;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    public AuthorServiceImpl() {
        this.authorDao = Injector.getInstance().getService(AuthorDao.class);
    }

    public Author add(String name) {
        return authorDao.create(name);
    }

    public void remove(Author author) {
        authorDao.delete(author.getId());
    }

    public List<Author> getAll() {
        return authorDao.getAll();
    }

    public List<Author> getByName(String name) {
        return getAll().stream()
                .filter(author -> author.getName().equals(name))
                .toList();
    }
}
