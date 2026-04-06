package ru.vtv.hw.practical.taskmaster;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Data
public class Task {
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;
}
