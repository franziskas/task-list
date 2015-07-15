package com.codurance.training.tasks;

import java.util.List;
import java.util.Map;

public class ProjectsToTasks {
    private final Map<String, List<Task>> tasks;

    public ProjectsToTasks(Map<String, List<Task>> tasks) {
        this.tasks = tasks;
    }


    public void addDeadline(long taskId, TaskDate deadline) {
        for (Map.Entry<String, List<Task>> projectToTasks : tasks.entrySet()) {
            projectToTasks.getValue().stream()
                    .filter(task -> task.hasId(taskId))
                    .findFirst()
                    .ifPresent(task -> {
                        projectToTasks.getValue().remove(task);
                        projectToTasks.getValue().add(task.withDeadline(deadline));
                    });

        }
    }

    public void printDueTasks(TaskListConsole console, TaskDate today) {
        tasks.values().stream()
                .flatMap(allTasks -> allTasks.stream())
                .filter(task -> task.isDueAt(today))
                .forEach(dueTask -> console.printWithNewLine("    [%c] %d: %s%n", ' ', dueTask.getId(), dueTask.getDescription()));

    }
}
