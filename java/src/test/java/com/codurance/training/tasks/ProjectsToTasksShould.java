package com.codurance.training.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.mockito.Mock;

import static java.time.LocalDate.now;
import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ProjectsToTasksShould {
    private static final String PROJECT = "myProject";
    private static final TaskDate DEADLINE = new TaskDate(now());
    public static final long TASK_ID = 42L;
    public static final String TASK_DESCRIPTION = "description";

    @Mock
    private TaskListConsole console = mock(TaskListConsole.class);

    @Test
    public void
    print_due_tasks_to_console() {
        Task dueTask = new Task(TASK_ID, TASK_DESCRIPTION, false).withDeadline(DEADLINE);
        Task notDueTask = new Task(56L, "notDue", false).withDeadline(new TaskDate(now().plusDays(2)));
        Task taskWithoutDeadline = new Task(156L, "noDeadline", false);
        Map<String, List<Task>> tasks = createTasksWith(PROJECT, dueTask, notDueTask, taskWithoutDeadline);
        ProjectsToTasks projectsToTasks = new ProjectsToTasks(tasks);

        projectsToTasks.printDueTasks(console, DEADLINE);

        verify(console).printWithNewLine("    [%c] %d: %s%n", ' ', TASK_ID, TASK_DESCRIPTION);
    }


    @Test
    public void
    add_a_deadline_to_task_with_Id() {
        Task myTask = new Task(TASK_ID, TASK_DESCRIPTION, false);
        Map<String, List<Task>> tasks = createTasksWith(PROJECT, myTask);
        ProjectsToTasks projectsToTasks = new ProjectsToTasks(tasks);

        projectsToTasks.addDeadline(TASK_ID, DEADLINE);

        Task expectedTask = myTask.withDeadline(DEADLINE);
        assertThat(tasks.get(PROJECT), contains(expectedTask));
    }

    private Map<String, List<Task>> createTasksWith(String project, Task... myTasks) {
        Map<String, List<Task>> tasks = new HashMap<>();
        tasks.put(project, new ArrayList<>(asList(myTasks)));
        return tasks;
    }
}
