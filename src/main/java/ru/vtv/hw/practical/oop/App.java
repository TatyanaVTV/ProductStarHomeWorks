package ru.vtv.hw.practical.oop;

import ru.vtv.hw.practical.oop.cli.Injector;
import ru.vtv.hw.practical.oop.cli.commands.ICommand;
import ru.vtv.hw.practical.oop.cli.menu.MainMenu;
import ru.vtv.hw.practical.oop.cli.service.DataService;

public class App {

    private static App instance;

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    private ICommand command;

    App() {
        this.command = new MainMenu();

        var dataService = Injector.getInstance().getService(DataService.class);
        dataService.initializeData();
    }

    public void run() {
        while (command != null) {
            command = command.execute();
        }
    }
}
