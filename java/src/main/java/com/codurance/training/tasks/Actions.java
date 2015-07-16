package com.codurance.training.tasks;

import java.util.Map;

public class Actions {
    private final Map<ActionIdentifier, Runnable> actions;
    private final Runnable defaultAction;

    public Actions(Map<ActionIdentifier, Runnable> actions, Runnable defaultAction) {
        this.actions = actions;
        this.defaultAction = defaultAction;
    }

    public void execute(ActionIdentifier actionIdentifier) {
        actions.getOrDefault(actionIdentifier, defaultAction).run();
    }
}
