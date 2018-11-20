package com.akhambir.puzzle.processor;

import com.akhambir.puzzle.driver.Request;
import com.akhambir.puzzle.driver.Response;
import com.akhambir.puzzle.presentation.CommandLineResponseHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static com.akhambir.puzzle.driver.Response.Result.FINISHED;
import static com.akhambir.puzzle.driver.Response.Result.NEXT_TURN;
import static com.akhambir.puzzle.driver.Response.Result.WRONG_REQUEST;
import static com.akhambir.puzzle.processor.GameProcessorImplFunctions.checkDistance;
import static com.akhambir.puzzle.processor.GameProcessorImplFunctions.defineResult;
import static com.akhambir.puzzle.processor.GameProcessorImplFunctions.doTurn;
import static com.akhambir.puzzle.processor.GameProcessorImplFunctions.handleResponse;
import static com.akhambir.puzzle.processor.GameProcessorImplFunctions.processRequest;
import static com.akhambir.puzzle.processor.GameProcessorImplFunctions.updateCurrentState;
import static com.akhambir.puzzle.processor.GameProcessorImplFunctions.validateTurn;
import static com.akhambir.puzzle.util.Constants.FIRST_TILE;
import static com.akhambir.puzzle.util.Constants.INVALID_INPUT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class GameProcessorImplFunctionsTest {

    @Mock
    private CommandLineResponseHandler responseHandler;

    private int[] gameState = {-1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 15};

    @Test
    public void testValidateTurnWhenWrongRequest() {
        Request request = Request.of(INVALID_INPUT);
        Request actual = validateTurn.apply(request).apply(gameState);
        assertEquals(false, actual.isValid());
    }

    @Test
    public void testValidateTurn_WhenInvalidDistance() {
        int invalidInput = 13;
        Request request = Request.of(invalidInput);
        Request actual = validateTurn.apply(request).apply(gameState);
        assertEquals(false, actual.isValid());
    }

    @Test
    public void testValidateTurnWhenValidRequest() {
        Request request = Request.of(14);
        Request actual = validateTurn.apply(request).apply(gameState);
        assertEquals(true, actual.isValid());
    }

    @Test
    public void testCheckDistanceOnValidRequest() {
        int validInput = 14;
        boolean actual = checkDistance.apply(validInput).apply(gameState);
        assertEquals(true, actual);
    }

    @Test
    public void testCheckDistanceOnInvalidRequest() {
        int invalidInput = 13;
        boolean actual = checkDistance.apply(invalidInput).apply(gameState);
        assertEquals(false, actual);
    }

    @Test
    public void testProcessRequest_ShouldReturnNextTurnResponseOnValidRequest() {
        int validInput = 14;
        Request request = Request.of(validInput);
        Response response = processRequest.apply(request).apply(gameState);
        assertEquals(NEXT_TURN, response.getResult());
    }

    @Test
    public void testProcessRequest_ShouldReturnFinishedResponseOnFinalValidRequest() {
        int validInput = 15;
        Request request = Request.of(validInput);
        Response response = processRequest.apply(request).apply(gameState);
        assertEquals(FINISHED, response.getResult());
    }

    @Test
    public void testProcessRequest_ShouldReturnWrongRequestResponseOnInvalidRequest() {
        Request request = Request.of(INVALID_INPUT);
        Response response = processRequest.apply(request).apply(gameState);
        assertEquals(WRONG_REQUEST, response.getResult());
    }

    @Test
    public void testDefineResult_ShouldReturnNextTurnOnIntermediateGameState() {
        Response.Result result = defineResult.apply(gameState, FIRST_TILE);
        assertEquals(NEXT_TURN, result);
    }

    @Test
    public void testDefineResult_ShouldReturnFinishedOnFinalGameState() {
        int[] finalGameState = {-1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};
        Response.Result result = defineResult.apply(finalGameState, FIRST_TILE);
        assertEquals(FINISHED, result);
    }

    @Test
    public void testDoTurnOnHorizontalMove() {
        int validInput = 14;
        Request request = Request.of(validInput);
        int[] expected = {-1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 0, 14, 15};
        int[] actual = doTurn.apply(request).apply(gameState);

        assertEquals(true, Arrays.equals(expected, actual));
    }

    @Test
    public void testDoTurnOnVerticalMove() {
        int validInput = 11;
        Request request = Request.of(validInput);
        int[] expected = {-1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 12, 13, 14, 11, 15};
        int[] actual = doTurn.apply(request).apply(gameState);

        assertEquals(true, Arrays.equals(expected, actual));
    }

    @Test
    public void testUpdateCurrentState_ShouldAddNewStateToTheQueue() {
        Response response = Response.of(gameState, NEXT_TURN);
        Queue<int[]> gameStateHolder = new LinkedList<>();
        updateCurrentState.apply(response).apply(gameStateHolder);
        assertEquals(true, Arrays.equals(gameState, gameStateHolder.peek()));
    }

    @Test
    public void testUpdateCurrentState_ShouldReturnSameResponseAsConsumed() {
        Response response = Response.of(gameState, NEXT_TURN);
        Queue<int[]> gameStateHolder = new LinkedList<>();
        Response actual = updateCurrentState.apply(response).apply(gameStateHolder);
        assertEquals(NEXT_TURN, actual.getResult());
        assertEquals(gameState, actual.getGameState());
    }

    @Test
    public void testHandleResponse() {
        doNothing().when(responseHandler).handle(any(Response.class));

        Response response = Response.of(gameState, NEXT_TURN);
        handleResponse.apply(response).accept(responseHandler);
        ArgumentCaptor<Response> argumentCaptor = ArgumentCaptor.forClass(Response.class);
        verify(responseHandler).handle(argumentCaptor.capture());

        Response captured = argumentCaptor.getValue();
        assertEquals(NEXT_TURN, captured.getResult());
        assertEquals(gameState, captured.getGameState());
    }
}