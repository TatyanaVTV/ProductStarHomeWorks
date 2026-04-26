package ru.vtv.hw.practical.niofile.menu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Command {
    private final Action action;
}
