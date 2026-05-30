package ru.vtv.hw.practical.oop.cli.menu;

import ru.vtv.hw.practical.oop.cli.commands.BookSearchByTitleCommand;
import ru.vtv.hw.practical.oop.cli.commands.BaseCommand;
import ru.vtv.hw.practical.oop.cli.commands.ICommand;
import ru.vtv.hw.practical.oop.cli.commands.ViewAllBooksCommand;

import java.util.concurrent.atomic.AtomicInteger;

public class MainMenu extends BaseCommand {

    public MainMenu() {
        initializeMenu();
    }

    private void initializeMenu() {
        final AtomicInteger menuItemNumber = new AtomicInteger(1);

        commandSuppliers.put(menuItemNumber.getAndIncrement(), ViewAllBooksCommand::new);
        commandSuppliers.put(menuItemNumber.getAndIncrement(), BookSearchByTitleCommand::new);
        commandSuppliers.put(menuItemNumber.getAndIncrement(), () -> null);
    }

    @Override
    public ICommand execute() {
        return selectMenu();
    }

    @Override
    public String getDescription() {
        return "Главное меню";
    }
}


