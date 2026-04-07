package ru.vtv.hw.practical.adventurer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Command {
    private final Action action;
}
