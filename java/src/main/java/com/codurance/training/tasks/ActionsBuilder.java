package com.codurance.training.tasks;

import java.util.HashMap;
import java.util.Map;

public class ActionsBuilder {

    private Map<String, Runnable> actions = new HashMap<>();
    private Runnable defaultAction = () -> {};

    public ActionsBuilder withAction(String identifier, Runnable action) {
        actions.put(identifier, action);
        return this;
    }

    public ActionsBuilder withDefault(Runnable defaultAction) {
        this.defaultAction = defaultAction;
        return this;
    }

    public Actions build() {
        return new Actions(actions, defaultAction);
    }
}
