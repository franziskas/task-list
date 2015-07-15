package com.codurance.training.tasks;

import static java.lang.Long.parseLong;
import static java.time.LocalDate.parse;

public class DeadlineCommand {
    private final long taskId;
    private final TaskDate deadline;

    public DeadlineCommand(String[] command) {
        String[] subcommandRest = command[1].split(" ", 2);
        this.taskId = parseLong(subcommandRest[0]);
        this.deadline = new TaskDate(parse(subcommandRest[1]));
    }

    public void executeOn(ProjectsToTasks projectsToTasks) {
        projectsToTasks.addDeadline(taskId, deadline);

    }
}
