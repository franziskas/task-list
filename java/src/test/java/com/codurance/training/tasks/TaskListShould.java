package com.codurance.training.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class TaskListShould {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private PrintWriter writer = mock(PrintWriter.class);
    private BufferedReader reader = mock(BufferedReader.class);

    private TaskList taskList = new TaskList(reader, writer);

    @Test
    public void
    change_io_exception_in_runtime_exception() throws Exception {
        final IOException ioException = new IOException();
        doThrow(ioException).when(reader).readLine();

        thrown.expect(RuntimeException.class);
        thrown.expectCause(is(ioException));

        taskList.run();
    }

    @Test
    public void
    print_prompt() throws Exception {
        when(reader.readLine())
                .thenReturn("quit");

        taskList.run();

        verify(writer).print("> ");
    }

    @Test
    public void
    flush() throws Exception {
        when(reader.readLine())
                .thenReturn("quit");

        taskList.run();

        verify(writer).flush();
    }

    @Test
    public void
    stop_execution_on_quit_command() throws Exception {
        when(reader.readLine())
                .thenReturn("quit");

        taskList.run();

        verify(reader, times(1)).readLine();
        verifyNoMoreInteractions(reader);
    }

    @Test
    public void
    continue_execution_on_other_command() throws Exception {
        when(reader.readLine())
                .thenReturn("show")
                .thenReturn("quit");

        taskList.run();

        verify(reader, times(2)).readLine();
        verifyNoMoreInteractions(reader);
    }

    @Test
    public void
    display_error_on_unknown_command() throws Exception {
        String command = "unknown";
        when(reader.readLine())
                .thenReturn(command)
                .thenReturn("quit");

        taskList.run();

        verify(writer).printf("I don't know what the command \"%s\" is.", command);
        verify(writer).println();
    }

    @Test
    public void
    display_list_of_available_commands_on_help_command() throws Exception {
        when(reader.readLine())
                .thenReturn("help")
                .thenReturn("quit");

        taskList.run();

        verify(writer).println("Commands:");
        verify(writer).println("  show");
        verify(writer).println("  add project <project name>");
        verify(writer).println("  add task <project name> <task description>");
        verify(writer).println("  check <task ID>");
        verify(writer).println("  uncheck <task ID>");
        verify(writer).println();
    }

    @Test
    public void
    show_error_message_when_adding_task_to_unknown_project() throws Exception {
        String project = "unknown";
        when(reader.readLine())
                .thenReturn("add task " + project + " mytask")
                .thenReturn("quit");

        taskList.run();

        verify(writer).printf("Could not find a project with the name \"%s\".", project);
        verify(writer).println();
    }

    @Test
    public void
    show_added_projects_on_show() throws Exception {
        String aProject = "aProject";
        String anotherProject = "anotherProject";
        when(reader.readLine())
                .thenReturn("add project " + aProject)
                .thenReturn("add project " + anotherProject)
                .thenReturn("show")
                .thenReturn("quit");

        taskList.run();

        verify(writer).println(aProject);
        verify(writer, times(2)).println();
        verify(writer).println(anotherProject);
    }

    @Test
    public void
    show_added_task_on_show() throws Exception {
        String aProject = "aProject";
        String aTask = "aTask";
        when(reader.readLine())
                .thenReturn("add project " + aProject)
                .thenReturn("add task " + aProject + " " + aTask)
                .thenReturn("show")
                .thenReturn("quit");

        taskList.run();

        verify(writer).println(aProject);
        verify(writer).printf("    [%c] %d: %s%n", ' ', 1L, aTask);
        verify(writer).println();
    }

    @Test
    public void
    show_error_message_when_checking_unknown_task() throws Exception {
        int taskId = 1;
        when(reader.readLine())
                .thenReturn("check " + taskId)
                .thenReturn("quit");

        taskList.run();

        verify(writer).printf("Could not find a task with an ID of %d.", taskId);
        verify(writer).println();
    }

    @Test
    public void
    show_error_message_when_unchecking_unknown_task() throws Exception {
        String aProject = "aProject";
        int taskId = 42;
        when(reader.readLine())
                .thenReturn("add project " + aProject)
                .thenReturn("add task " + aProject + " aTask")
                .thenReturn("uncheck " + taskId)
                .thenReturn("quit");

        taskList.run();

        verify(writer).printf("Could not find a task with an ID of %d.", taskId);
        verify(writer).println();
    }

    @Test
    public void
    show_checked_task_on_show() throws Exception {
        String aProject = "aProject";
        String aTask = "aTask";
        when(reader.readLine())
                .thenReturn("add project " + aProject)
                .thenReturn("add task " + aProject + " " + aTask)
                .thenReturn("check 1")
                .thenReturn("show")
                .thenReturn("quit");

        taskList.run();

        verify(writer).println(aProject);
        verify(writer).printf("    [%c] %d: %s%n", 'x', 1L, aTask);
        verify(writer).println();
    }

    @Test
    public void
    show_unchecked_task_on_show() throws Exception {
        String aProject = "aProject";
        String aTask = "aTask";
        when(reader.readLine())
                .thenReturn("add project " + aProject)
                .thenReturn("add task " + aProject + " " + aTask)
                .thenReturn("check 1")
                .thenReturn("uncheck 1")
                .thenReturn("show")
                .thenReturn("quit");

        taskList.run();

        verify(writer).println(aProject);
        verify(writer).printf("    [%c] %d: %s%n", ' ', 1L, aTask);
        verify(writer).println();
    }
}
