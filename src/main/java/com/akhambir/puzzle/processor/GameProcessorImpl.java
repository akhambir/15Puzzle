package com.akhambir.puzzle.processor;

import com.akhambir.puzzle.driver.Request;
import com.akhambir.puzzle.presentation.interfaces.ResponseHandler;
import com.akhambir.puzzle.processor.interfaces.GameProcessor;

import java.util.LinkedList;
import java.util.Queue;

import static com.akhambir.puzzle.processor.GameProcessorImplFunctions.handleResponse;
import static com.akhambir.puzzle.processor.GameProcessorImplFunctions.validateTurn;
import static com.akhambir.puzzle.processor.GameProcessorImplFunctions.processRequest;
import static com.akhambir.puzzle.processor.GameProcessorImplFunctions.updateCurrentState;

public class GameProcessorImpl implements GameProcessor {

    private final Queue<int[]> gameStateHolder = new LinkedList<>();

    private final ResponseHandler responseHandler;


    public GameProcessorImpl(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    @Override
    public void process(Request request) {
        validateTurn
                .apply(request)
                .andThen(processRequest)
                .apply(gameStateHolder.peek())
                .andThen(updateCurrentState)
                .apply(gameStateHolder.poll())
                .andThen(handleResponse)
                .apply(gameStateHolder)
                .accept(responseHandler);
    }

    public void setInitialGameState(int[] initialGameState) {
        gameStateHolder.add(initialGameState);
    }
}
