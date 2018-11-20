package com.akhambir.puzzle.processor;


import com.akhambir.puzzle.driver.Request;
import com.akhambir.puzzle.driver.Response;
import com.akhambir.puzzle.presentation.CommandLineResponseHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static com.akhambir.puzzle.driver.Response.Result.NEXT_TURN;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GameProcessorImplTest {

    @Mock
    private CommandLineResponseHandler responseHandler;

    private GameProcessorImpl gameProcessor;

    private int[] gameState = {-1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 15};

    @Before
    public void init() {
        gameProcessor = new GameProcessorImpl(responseHandler);
        gameProcessor.setInitialGameState(gameState);
    }

    @Test
    public void testProcess_PositiveScenario() {
        int validInput = 14;
        Request request = Request.of(validInput);

        doNothing().when(responseHandler).handle(any(Response.class));
        gameProcessor.process(request);

        int[] expectedGameState = {-1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 0, 14, 15};
        ArgumentCaptor<Response> argumentCaptor = ArgumentCaptor.forClass(Response.class);

        verify(responseHandler).handle(argumentCaptor.capture());
        Response captured = argumentCaptor.getValue();
        assertEquals(NEXT_TURN, captured.getResult());
        assertEquals(true, Arrays.equals(expectedGameState, captured.getGameState()));
    }
}