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
    should_change_io_exception_in_runtime_exception() throws Exception {
        final IOException ioException = new IOException();
        doThrow(ioException).when(reader).readLine();

        thrown.expect(RuntimeException.class);
        thrown.expectCause(is(ioException));

        taskList.run();
    }

    @Test
    public void
    should_print_prompt() throws Exception {
        when(reader.readLine()).thenReturn("quit");

        taskList.run();

        verify(writer).print("> ");
    }

    @Test
    public void
    should_flush() throws Exception {
        when(reader.readLine()).thenReturn("quit");

        taskList.run();

        verify(writer).flush();
    }

    @Test
    public void
    should_stop_execution_on_quit_command() throws Exception {
        when(reader.readLine()).thenReturn("quit");

        taskList.run();

        verify(reader, times(1)).readLine();
        verifyNoMoreInteractions(reader);
    }

    @Test
    public void
    should_continue_execution_on_other_command() throws Exception {
        when(reader.readLine()).thenReturn("show").thenReturn("quit");

        taskList.run();

        verify(reader, times(2)).readLine();
        verifyNoMoreInteractions(reader);
    }

}
