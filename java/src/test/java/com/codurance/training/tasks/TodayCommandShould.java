package com.codurance.training.tasks;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TodayCommandShould {
    @Mock
    private TaskListConsole console = mock(TaskListConsole.class);
    @Mock
    private ProjectsToTasks projectsToTasks = mock(ProjectsToTasks.class);
    @Mock
    private Clock clock = mock(Clock.class);

    @Test
    public void
    print_tasks_that_are_due_today_on_projectsToTasks() {
        String today = "2020-01-01";
        mockTodayTo(today);
        TaskDate todaysDate = new TaskDate(LocalDate.parse(today));

        TodayCommand command = new TodayCommand(clock, console);

        command.executeOn(projectsToTasks);

        verify(projectsToTasks).printDueTasks(console, todaysDate);
    }

    private void mockTodayTo(String today) {
        when(clock.instant()).thenReturn(Instant.parse(today + "T00:00:00Z"));
        when(clock.getZone()).thenReturn(Clock.systemDefaultZone().getZone());
    }
}
