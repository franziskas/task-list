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
}
