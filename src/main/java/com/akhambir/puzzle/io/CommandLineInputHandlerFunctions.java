package com.akhambir.puzzle.io;

import com.akhambir.puzzle.driver.Request;
import com.akhambir.puzzle.processor.interfaces.GameProcessor;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static com.akhambir.puzzle.util.Constants.GAME_OVER;
import static com.akhambir.puzzle.util.Constants.HIGHEST_CELL_VALUE;
import static com.akhambir.puzzle.util.Constants.INVALID_INPUT;
import static com.akhambir.puzzle.util.Constants.SMALLEST_CELL_VALUE;

final class CommandLineInputHandlerFunctions {

    private CommandLineInputHandlerFunctions() {}


    static Function<String, Integer> parseInput = s -> {
        int inputIntegerValue;

        try {
            inputIntegerValue = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            inputIntegerValue = INVALID_INPUT;
        }

        return inputIntegerValue;
    };

    static UnaryOperator<Integer> validateInput = i ->
            i >= SMALLEST_CELL_VALUE && i <= HIGHEST_CELL_VALUE ? i : INVALID_INPUT;

    static Function<Request, Consumer<GameProcessor>> processGameRequest = r -> gp -> gp.process(r);

    static Function<String, Consumer<GameProcessor>> handleNextTurnInput = parseInput
            .andThen(validateInput)
            .andThen(Request::of)
            .andThen(processGameRequest);

    static Function<String, Consumer<GameProcessor>> termination = s -> cs -> {
        cs.terminate(GAME_OVER);
    };
}
