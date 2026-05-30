package ru.vtv.hw.practical.oop.cli.commands;

public interface ICommand {
    ICommand execute();
    String getDescription();
}
