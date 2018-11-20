package com.akhambir.puzzle.presentation;

import com.akhambir.puzzle.driver.Response;
import com.akhambir.puzzle.presentation.interfaces.ResponseHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static com.akhambir.puzzle.driver.Response.Result.FINISHED;
import static com.akhambir.puzzle.driver.Response.Result.WRONG_REQUEST;
import static com.akhambir.puzzle.presentation.CommandLineResponseHandlerFunctions.whenFinished;
import static com.akhambir.puzzle.presentation.CommandLineResponseHandlerFunctions.whenNextTurn;
import static com.akhambir.puzzle.presentation.CommandLineResponseHandlerFunctions.whenWrongRequest;

public class CommandLineResponseHandler implements ResponseHandler {

    private static final Map<Response.Result, Consumer<int[]>> functionsMap = new HashMap<>();

    static {
        functionsMap.put(WRONG_REQUEST, whenWrongRequest);
        functionsMap.put(FINISHED, whenFinished);
    }


    @Override
    public void handle(Response response) {
        functionsMap.getOrDefault(response.getResult(), whenNextTurn).accept(response.getGameState());
    }
}
