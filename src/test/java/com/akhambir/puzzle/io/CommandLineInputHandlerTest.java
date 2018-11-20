package com.akhambir.puzzle.io;

import com.akhambir.puzzle.driver.Request;
import com.akhambir.puzzle.processor.GameProcessorImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineInputHandlerTest {

    @Mock
    private GameProcessorImpl gameProcessor;

    private CommandLineInputHandler inputHandler;

    @Before
    public void init() {
        String validInput = "14";
        ByteArrayInputStream in = new ByteArrayInputStream(validInput.getBytes());
        inputHandler = new CommandLineInputHandler(gameProcessor, new Scanner(in));
    }

    @Test
    public void testHandle_PositiveScenario() {
        doNothing().when(gameProcessor).process(any(Request.class));

        inputHandler.handle();

        ArgumentCaptor<Request> argumentCaptor = ArgumentCaptor.forClass(Request.class);
        verify(gameProcessor).process(argumentCaptor.capture());

        Request actual = argumentCaptor.getValue();

        assertEquals(14, actual.getInput());
        assertEquals(true, actual.isValid());
    }
}