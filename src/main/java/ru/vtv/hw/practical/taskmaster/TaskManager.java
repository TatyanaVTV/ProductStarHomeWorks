package ru.vtv.hw.practical.taskmaster;

import java.util.HashSet;
import java.util.Set;

public class TaskManager {
    private final Set<Project> projectSet = new HashSet<>();

    public void addProject(Project project) {
        projectSet.add(project);
    }

    public void displayAllProjects() {
        projectSet.forEach(System.out::println);
    }
}
