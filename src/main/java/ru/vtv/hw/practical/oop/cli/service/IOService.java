package ru.vtv.hw.practical.oop.cli.service;

import ru.vtv.hw.practical.oop.domain.model.Book;

import java.util.List;
import java.util.Map;

public interface IOService {
    String readLine();
    void printLine(String line);
    void close();

    void printBooks(List<Book> books);

    void printMenu(String title, Map<Integer, String> items);
    void printPromt(String promptMessage);

    String prompt(String message);

    int promptForMenuSelection(Map<Integer, String> menuItems, String promptMessage);
}
