package com.codurance.training.tasks;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LegacyCommandShould {
    @Test
    public void
    run_the_given_runnable() {
        Runnable myRunnable = mock(Runnable.class);
        LegacyCommand legacyCommand = new LegacyCommand(myRunnable);

        legacyCommand.execute();

        verify(myRunnable).run();
    }
}
