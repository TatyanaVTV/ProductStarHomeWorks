package ru.vtv.hw.practical.detective;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class Command {
    private final Action action;
    private String data;
}
