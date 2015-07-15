package com.codurance.training.tasks;

import org.junit.Test;
import org.mockito.Mock;

import static java.time.LocalDate.of;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DeadlineCommandShould {
    @Mock
    private ProjectsToTasks projectsToTasks = mock(ProjectsToTasks.class);
    
    @Test
    public void
    add_deadline_to_task() {
        String[] parameter = {"", "42 2012-02-02"};
        DeadlineCommand command = new DeadlineCommand(parameter);

        command.executeOn(projectsToTasks);

        verify(projectsToTasks).addDeadline(42L, new TaskDate(of(2012, 2, 2)));
    }


}
