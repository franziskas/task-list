package com.codurance.training.tasks;

import static java.time.LocalDate.MAX;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public final class Task {
    private final long id;
    private final String description;
    private final TaskDate deadline;
    private boolean done;

    public Task(long id, String description, boolean done) {
        this(id, description, done, new TaskDate(MAX));
    }

    public Task withDeadline(TaskDate deadline) {
        return new Task(this.id, this.description, this.done, deadline);
    }

    private Task(long id, String description, boolean done, TaskDate deadline) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.deadline = deadline;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDueAt(TaskDate date) {
        return deadline.equals(date);
    }

    public boolean hasId(long taskId) {
        return this.id == taskId;
    }

    @Override
    public boolean equals(Object obj) {
        return reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }
}
