package com.codurance.training.tasks;

public class LegacyCommand {
    private final Runnable runnable;

    public LegacyCommand(Runnable runnable) {
        this.runnable = runnable;
    }

    public void execute() {
        runnable.run();
    }
}
