package com.codurance.training.tasks;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class ActionsShould {
    @Test
    public void
    run_default_action_if_there_is_no_matching_action() {
        Runnable defaultRunnable = mock(Runnable.class);
        Actions actions = new ActionsBuilder()
                .withDefault(defaultRunnable)
                .build();

        actions.execute(new ActionIdentifier("unknown"));

        verify(defaultRunnable).run();
    }

    @Test
    public void
    run_only_action_matching_command() {
        Runnable matchingRunnable = mock(Runnable.class);
        Runnable notMatchingRunnable = mock(Runnable.class);
        Runnable defaultRunnable = mock(Runnable.class);
        String identifier = "matchingIdentifier";
        Actions actions = new ActionsBuilder()
                .withAction(new ActionIdentifier(identifier), matchingRunnable)
                .withAction(new ActionIdentifier("differentIdentifier"), notMatchingRunnable)
                .withDefault(defaultRunnable)
                .build();

        actions.execute(new ActionIdentifier(identifier));

        verify(matchingRunnable).run();
        verifyZeroInteractions(notMatchingRunnable);
        verifyZeroInteractions(defaultRunnable);
    }
}
