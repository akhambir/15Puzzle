package com.akhambir.puzzle.processor;

import com.akhambir.puzzle.driver.Request;
import com.akhambir.puzzle.driver.Response;
import com.akhambir.puzzle.presentation.interfaces.ResponseHandler;
import com.akhambir.puzzle.util.function.Recursive;

import java.util.Arrays;
import java.util.Queue;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static com.akhambir.puzzle.driver.Response.Result.FINISHED;
import static com.akhambir.puzzle.driver.Response.Result.NEXT_TURN;
import static com.akhambir.puzzle.driver.Response.Result.WRONG_REQUEST;
import static com.akhambir.puzzle.util.Constants.EMPTY_CELL;
import static com.akhambir.puzzle.util.Constants.FIRST_TILE;
import static com.akhambir.puzzle.util.Constants.HIGHEST_CELL_VALUE;
import static com.akhambir.puzzle.util.Constants.HORIZONTAL_RANGE;
import static com.akhambir.puzzle.util.Constants.INVALID_INPUT;
import static com.akhambir.puzzle.util.Constants.VERTICAL_RANGE;
import static com.akhambir.puzzle.util.function.Functions.findPos;

final class GameProcessorImplFunctions {

    private GameProcessorImplFunctions() {}

    static Function<Request, Function<int[], Request>> validateTurn = r -> gs -> r.isValid()
            ? GameProcessorImplFunctions.checkDistance
                .apply(r.getInput())
                .apply(gs)
                    ? Request.of(r.getInput())
                    : Request.of(INVALID_INPUT)
            : r;

    static Function<Integer, Function<int[], Boolean>> checkDistance = i -> gs -> {
        int chosenPos = findPos.apply(i).apply(gs);
        int emptyPos = findPos.apply(EMPTY_CELL).apply(gs);
        int distance = Math.abs(chosenPos - emptyPos);
        return distance == HORIZONTAL_RANGE || distance == VERTICAL_RANGE;
    };

    static Function<Request, Function<int[], Response>> processRequest = r -> gs -> {
        Response result;

        if (r.isValid()) {
            int[] newGs = GameProcessorImplFunctions.doTurn.apply(r).apply(gs);
            Response.Result res = GameProcessorImplFunctions.defineResult.apply(newGs, FIRST_TILE);
            result = Response.of(newGs, res);
        } else {
            result = Response.of(gs, WRONG_REQUEST);
        }

        return result;
    };

    static BiFunction<int[], Integer, Response.Result> defineResult = Recursive.biFunction((gs, i, self) -> gs[i] != i
                ? NEXT_TURN
                : i == HIGHEST_CELL_VALUE
                    ? FINISHED
                    : self.apply(gs, i + 1)
    );

    static Function<Request, UnaryOperator<int[]>> doTurn = r -> gs -> {
        int[] result = Arrays.copyOf(gs, gs.length);
        int chosenPos = findPos.apply(r.getInput()).apply(gs);
        int emptyPos = findPos.apply(EMPTY_CELL).apply(gs);

        result[emptyPos] = result[chosenPos];
        result[chosenPos] = EMPTY_CELL;

        return result;
    };

    static Function<Response, Function<Queue<int[]>, Response>> updateCurrentState = r -> d -> {
        d.add(r.getGameState());
        return r;
    };

    static Function<Response, Consumer<ResponseHandler>> handleResponse = r -> rh -> {
        rh.handle(r);
    };
}
