package com.codurance.training.tasks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.Clock;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.time.Clock.systemDefaultZone;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";

    private final Map<String, List<Task>> tasks = new LinkedHashMap<>();
    private final TaskListConsole console;
    private final Clock clock;

    private long lastId = 0;

    public TaskList(TaskListConsole console, Clock clock) {
        this.console = console;
        this.clock = clock;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new TaskList(new TaskListConsole(in, out), systemDefaultZone()).run();
    }


    public void run() {
        while (true) {
            String command = console.readCommand();
            if (command.equals(TaskList.QUIT)) {
                break;
            }
            execute(command);
        }
    }

    private void execute(String commandLine) {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "show":
                show();
                break;
            case "add":
                add(commandRest[1]);
                break;
            case "check":
                check(commandRest[1]);
                break;
            case "uncheck":
                uncheck(commandRest[1]);
                break;
            case "help":
                help();
                break;
            case "deadline":
                new DeadlineCommand(commandRest).executeOn(new ProjectsToTasks(tasks));
                break;
            case "today":
                new TodayCommand(clock, console).executeOn(new ProjectsToTasks(tasks));
                break;
            default:
                error(command);
                break;
        }
    }

    private void show() {
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            String output = project.getKey();
            print(output);
            for (Task task : project.getValue()) {
                console.writer.printf("    [%c] %d: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            }
            console.printNewLine();
        }
    }

    private void print(String output) {
        console.writer.println(output);
    }

    private void add(String commandLine) {
        String[] subcommandRest = commandLine.split(" ", 2);
        String subcommand = subcommandRest[0];
        if (subcommand.equals("project")) {
            addProject(subcommandRest[1]);
        } else if (subcommand.equals("task")) {
            String[] projectTask = subcommandRest[1].split(" ", 2);
            addTask(projectTask[0], projectTask[1]);
        }
    }

    private void addProject(String name) {
        tasks.put(name, new ArrayList<Task>());
    }

    private void addTask(String project, String description) {
        List<Task> projectTasks = tasks.get(project);
        if (projectTasks == null) {
            console.printError("Could not find a project with the name \"%s\".", project);
            return;
        }
        projectTasks.add(new Task(nextId(), description, false));
    }

    private void check(String idString) {
        setDone(idString, true);
    }

    private void uncheck(String idString) {
        setDone(idString, false);
    }

    private void setDone(String idString, boolean done) {
        int id = Integer.parseInt(idString);
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            for (Task task : project.getValue()) {
                if (task.getId() == id) {
                    task.setDone(done);
                    return;
                }
            }
        }
        console.printError("Could not find a task with an ID of %d.", id);
    }

    private void help() {
        print("Commands:");
        print("  show");
        print("  add project <project name>");
        print("  add task <project name> <task description>");
        print("  check <task ID>");
        print("  uncheck <task ID>");
        console.printNewLine();
    }

    private void error(String command) {
        String message = "I don't know what the command \"%s\" is.";
        console.printError(message, command);
    }

    private long nextId() {
        return ++lastId;
    }
}
