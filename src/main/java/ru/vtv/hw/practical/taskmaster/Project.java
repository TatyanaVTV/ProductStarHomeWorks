package ru.vtv.hw.practical.taskmaster;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import static ru.vtv.hw.practical.taskmaster.ProjectException.projectHasUndoneTasks;
import static ru.vtv.hw.practical.taskmaster.ProjectException.noTasks;
import static ru.vtv.hw.practical.taskmaster.TaskStatus.*;

@Getter
@AllArgsConstructor
public class Project implements Manageable {
    private final String name;
    private TaskStatus status;

    private final Set<Task> tasks = new HashSet<>();

    public Set<Task> getTasks() {
        return new HashSet<>(tasks);
    }

    @Override
    public void assign() {
        if (tasks.isEmpty()) throw noTasks();

        tasks.stream()
                .filter(task -> TODO.equals(task.getStatus()))
                .forEach(task -> task.setStatus(ASSIGNED));

        status = ASSIGNED;
    }

    @Override
    public void start() {
        if (tasks.isEmpty()) throw noTasks();

        tasks.stream()
                .filter(task -> ASSIGNED.equals(task.getStatus()))
                .forEach(task -> task.setStatus(IN_PROGRESS));

        status = IN_PROGRESS;
    }

    @Override
    public void complete() {
        var hasUndoneTasks = tasks.stream().anyMatch(task -> DONE.equals(task.getStatus()));
        if (hasUndoneTasks) throw projectHasUndoneTasks();

        status = DONE;
    }

    public static class TaskComparator implements Comparator<Task> {
        private final TaskCompareCriteria criteria;

        public TaskComparator(TaskCompareCriteria criteria) {
            this.criteria = criteria;
        }

        @Override
        public int compare(Task t1, Task t2) {
            return switch(criteria) {
                case TITLE ->  t1.getTitle().compareTo(t2.getTitle());
                case STATUS -> t1.getStatus().compareTo(t2.getStatus());
                case DUE_DATE ->  t1.getDueDate().compareTo(t2.getDueDate());
            };
        }
    }
}
