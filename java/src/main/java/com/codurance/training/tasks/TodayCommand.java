package com.codurance.training.tasks;

import java.time.Clock;

import static java.time.LocalDate.now;

public class TodayCommand {
    private final Clock clock;
    private final TaskListConsole console;

    public TodayCommand(Clock clock, TaskListConsole console) {
        this.clock = clock;
        this.console = console;
    }

    public void executeOn(ProjectsToTasks projectsToTasks) {
        projectsToTasks.printDueTasks(console, today());
    }

    private TaskDate today() {
        return new TaskDate(now(clock));
    }
}
