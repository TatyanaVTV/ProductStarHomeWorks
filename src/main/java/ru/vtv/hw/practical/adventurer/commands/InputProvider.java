package ru.vtv.hw.practical.adventurer.commands;

public interface InputProvider {
    String getInput(String prompt);
    boolean getConfirmation(String prompt);
}
