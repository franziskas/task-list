package com.codurance.training.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.is;

public class TaskListShould {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void
    should_change_io_exception_in_runtime_exception() {
        final IOException ioException = new IOException();
        BufferedReader reader = new BufferedReader(new Reader() {
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                return 0;
            }

            @Override
            public void close() throws IOException {

            }
        }) {
            @Override
            public String readLine() throws IOException {
                throw ioException;
            }
        };
        PrintWriter writer = new PrintWriter(new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {

            }

            @Override
            public void flush() throws IOException {

            }

            @Override
            public void close() throws IOException {

            }
        });
        TaskList taskList = new TaskList(reader, writer);

        thrown.expect(RuntimeException.class);
        thrown.expectCause(is(ioException));

        taskList.run();
    }
}
