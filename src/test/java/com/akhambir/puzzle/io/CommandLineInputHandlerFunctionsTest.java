package com.akhambir.puzzle.io;

import com.akhambir.puzzle.driver.Request;
import com.akhambir.puzzle.processor.GameProcessorImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.akhambir.puzzle.io.CommandLineInputHandlerFunctions.parseInput;
import static com.akhambir.puzzle.io.CommandLineInputHandlerFunctions.validateInput;
import static com.akhambir.puzzle.util.Constants.INVALID_INPUT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineInputHandlerFunctionsTest {

    @Mock
    private GameProcessorImpl gameProcessor;

    @Test
    public void testParseInputOnValidInput() {
        String validInput = "14";
        int actual = parseInput.apply(validInput);
        assertEquals(14, actual);
    }

    @Test
    public void testParseInputOnInvalidInput() {
        String invalidInput = "abc";
        int actual = parseInput.apply(invalidInput);
        assertEquals(INVALID_INPUT, actual);
    }

    @Test
    public void testValidateInputOnValidInput() {
        int validInput = 14;
        int actual = validateInput.apply(validInput);
        assertEquals(14, actual);
    }

    @Test
    public void testValidateInputOnInvalidInput() {
        int invalidInput = 100;
        int actual = validateInput.apply(invalidInput);
        assertEquals(INVALID_INPUT, actual);
    }

    @Test
    public void testProcessGameRequest() {
        int validInput = 14;
        Request request = Request.of(validInput);
        doNothing().when(gameProcessor).process(any(Request.class));

        gameProcessor.process(request);
        ArgumentCaptor<Request> argumentCaptor = ArgumentCaptor.forClass(Request.class);
        verify(gameProcessor).process(argumentCaptor.capture());

        Request captured = argumentCaptor.getValue();
        assertEquals(14, captured.getInput());
        assertEquals(true, captured.isValid());
    }
}