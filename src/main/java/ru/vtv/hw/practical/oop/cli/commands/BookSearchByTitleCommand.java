package ru.vtv.hw.practical.oop.cli.commands;

import ru.vtv.hw.practical.oop.cli.Injector;
import ru.vtv.hw.practical.oop.cli.menu.MainMenu;
import ru.vtv.hw.practical.oop.cli.service.BookService;
import ru.vtv.hw.practical.oop.domain.model.Book;

import java.util.List;

public class BookSearchByTitleCommand extends BaseCommand {
    private final BookService bookService;

    public BookSearchByTitleCommand() {
        this.bookService = Injector.getInstance().getService(BookService.class);
    }

    @Override
    public ICommand execute() {
        String searchQuery = ioService.prompt("Введите название книги для поиска: ");

        List<Book> books = bookService.getByTitle(searchQuery);

        if (books.isEmpty()) {
            ioService.printLine("Книги с указанным названием не найдены.");
        } else {
            ioService.printLine("Найденные книги:");
            ioService.printBooks(books);
        }

        return new MainMenu();
    }

    @Override
    public String getDescription() {
        return "Найти книгу по названию";
    }

}