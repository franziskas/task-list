package com.codurance.training.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class TaskListConsole {
    private final BufferedReader reader;
    private final PrintWriter writer;

    public TaskListConsole(BufferedReader reader, PrintWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    private void printPrompt() {
        writer.print("> ");
        writer.flush();
    }

    public String readCommand() {
        printPrompt();
        String command;
        try {
            command = getReader().readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return command;
    }
}
