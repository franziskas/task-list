package com.codurance.training.tasks;

import java.util.Map;

public class Actions {
    private final Map<String, Runnable> actions;
    private final Runnable defaultAction;

    public Actions(Map<String, Runnable> actions, Runnable defaultAction) {
        this.actions = actions;
        this.defaultAction = defaultAction;
    }

    public void execute(String command) {
        actions.getOrDefault(command, defaultAction).run();
    }
}
