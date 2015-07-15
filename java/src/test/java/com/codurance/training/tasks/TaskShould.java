package com.codurance.training.tasks;

import java.time.LocalDate;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TaskShould {

    public static final LocalDate SOME_DATE = LocalDate.of(2020, 3, 2);

    @Test
    public void
    be_due_at_date_if_deadline_is_date() {
        TaskDate deadline = new TaskDate(SOME_DATE);
        Task task = new Task(42L, "some description", false).withDeadline(deadline);

        assertThat(task.isDueAt(new TaskDate(SOME_DATE)), is(true));
    }

    @Test
    public void
    not_be_due_at_date_if_deadline_is_not_date() {
        TaskDate deadline = new TaskDate(SOME_DATE);
        Task task = new Task(42L, "some description", false).withDeadline(deadline);

        assertThat(task.isDueAt(new TaskDate(SOME_DATE.plusDays(1))), is(false));
    }

}

