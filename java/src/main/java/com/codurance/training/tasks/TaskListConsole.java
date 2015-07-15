package com.codurance.training.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class TaskListConsole {
    private final BufferedReader reader;
    public final PrintWriter writer;

    public TaskListConsole(BufferedReader reader, PrintWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }


    public BufferedReader getReader() {
        return reader;
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

    public void printNewLine() {
        writer.println();
    }

    public void printError(String message, Object... parameters) {
        writer.printf(message, parameters);
        printNewLine();
    }

    public void printWithNewLine(String message, char character, long id, Object object) {
        writer.printf(message, character, id, object);
        writer.println();
    }
}
