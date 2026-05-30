package ru.vtv.hw.practical.oop.cli.service;

import ru.vtv.hw.practical.oop.cli.Injector;

public class DataServiceImpl implements DataService {
    private final AuthorService authorService;
    private final BookService bookService;
    private final CategoryService categoryService;

    public DataServiceImpl() {
        this.authorService = Injector.getInstance().getService(AuthorService.class);
        this.bookService = Injector.getInstance().getService(BookService.class);
        this.categoryService = Injector.getInstance().getService(CategoryService.class);
    }

    @Override
    public void initializeData() {
        // categories
        var education = categoryService.add("Образование");
        var classic = categoryService.add("Классика");
        var scienceFiction = categoryService.add("Фантастика");

        // authors
        var carnegy = authorService.add("Дейл Карнеги");
        var oakley = authorService.add("Барбара Оакли");
        var kaufman = authorService.add("Джош Кауфман");

        var tolstoy = authorService.add("Лев Толстой");
        var dostoevsky = authorService.add("Фёдор Достоевский");
        var pushkin = authorService.add("Александр Пушкин");

        var hainline = authorService.add("Роберт Хайнлайн");
        var bradbery = authorService.add("Рэй Брэдбери");
        var herbert = authorService.add("Фрэнк Герберт");

        // books
        bookService.add("Как завоёвывать друзей и оказывать влияние на людей", carnegy, education);
        bookService.add("Думай как математик. Как решать любые задачи быстрее и эффективнее", oakley, education);
        bookService.add("Первые 20 часов. Как научиться чему угодно… быстро", kaufman, education);

        bookService.add("Война и мир", tolstoy, classic);
        bookService.add("Преступление и наказание", dostoevsky, classic);
        bookService.add("Евгений Онегин", pushkin, classic);

        bookService.add("Звёздный десант", hainline, scienceFiction);
        bookService.add("451 градус по Фаренгейту", bradbery, scienceFiction);
        bookService.add("Дюна", herbert, scienceFiction);
    }
}
