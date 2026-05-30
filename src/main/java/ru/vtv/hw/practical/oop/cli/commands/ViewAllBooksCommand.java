package ru.vtv.hw.practical.oop.cli.commands;

import ru.vtv.hw.practical.oop.cli.Injector;
import ru.vtv.hw.practical.oop.cli.menu.MainMenu;
import ru.vtv.hw.practical.oop.cli.service.BookService;
import ru.vtv.hw.practical.oop.domain.model.Book;

import java.util.List;

public class ViewAllBooksCommand extends BaseCommand {
    private final BookService bookService;

    public ViewAllBooksCommand() {
        this.bookService = Injector.getInstance().getService(BookService.class);
    }

    @Override
    public ICommand execute() {
        List<Book> books = bookService.getAll();

        if (books.isEmpty()) {
            ioService.printLine("Нет доступных книг для просмотра.");
        } else {
            ioService.printBooks(books);
        }

        return new MainMenu();
    }

    @Override
    public String getDescription() {
        return "Посмотреть все книги";
    }
}
