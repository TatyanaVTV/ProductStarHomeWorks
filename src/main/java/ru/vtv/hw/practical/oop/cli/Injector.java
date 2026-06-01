package ru.vtv.hw.practical.oop.cli;

import ru.vtv.hw.practical.oop.cli.service.*;
import ru.vtv.hw.practical.oop.repository.AuthorDao;
import ru.vtv.hw.practical.oop.repository.BookDao;
import ru.vtv.hw.practical.oop.repository.CategoryDao;
import ru.vtv.hw.practical.oop.repository.inmemory.AuthorDaoImpl;
import ru.vtv.hw.practical.oop.repository.inmemory.BookDaoImpl;
import ru.vtv.hw.practical.oop.repository.inmemory.CategoryDaoImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Injector {
    private static volatile Injector instance;

    public static Injector getInstance() {
        if (instance == null) {
            synchronized (Injector.class) {
                if (instance == null) {
                    instance = new Injector();
                }
            }
        }
        return instance;
    }

    private final Map<Class<?>, Supplier<?>> serviceFactories = new HashMap<>();
    private final Map<Class<?>, Object> serviceCache = new HashMap<>();

    private Injector() {
        registerService(AuthorService.class, AuthorServiceImpl::new);
        registerService(BookService.class, BookServiceImpl::new);
        registerService(CategoryService.class, CategoryServiceImpl::new);
        registerService(IOService.class, ConsoleIOService::new);

        registerService(AuthorDao.class, AuthorDaoImpl::new);
        registerService(BookDao.class, BookDaoImpl::new);
        registerService(CategoryDao.class, CategoryDaoImpl::new);

        registerService(DataService.class, DataServiceImpl::new);
    }

    public <T> void registerService(Class<T> serviceType, Supplier<? extends T> factory) {
        serviceFactories.put(serviceType, factory);
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> serviceType) {
        if (serviceCache.containsKey(serviceType)) {
            return (T) serviceCache.get(serviceType);
        } else {
            Supplier<?> factory = serviceFactories.get(serviceType);
            if (factory == null) {
                throw new RuntimeException("No factory registered for service: " + serviceType.getName());
            }

            T service = (T) factory.get();
            serviceCache.put(serviceType, service);

            return service;
        }


    }
}