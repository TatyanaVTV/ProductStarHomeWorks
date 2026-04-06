package ru.vtv.hw.practical.taskmaster;

public class ProjectException extends RuntimeException {
    private ProjectException(String message) {
        super(message);
    }

    public static ProjectException noTasks() {
        return new ProjectException("No tasks in this project!");
    }

    public static ProjectException projectHasUndoneTasks() {
        return new ProjectException("All tasks have to be done before completing project!");
    }
}
