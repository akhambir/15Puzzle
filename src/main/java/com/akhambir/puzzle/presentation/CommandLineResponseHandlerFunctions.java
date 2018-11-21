package com.akhambir.puzzle.presentation;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.akhambir.puzzle.util.Constants.FINAL_MSG;
import static com.akhambir.puzzle.util.Constants.WRONG_INPUT_MSG;
import static com.akhambir.puzzle.util.function.Functions.buildResponseFromState;
import static com.akhambir.puzzle.util.function.Functions.finishGame;
import static com.akhambir.puzzle.util.function.Functions.print;

final class CommandLineResponseHandlerFunctions {

    private CommandLineResponseHandlerFunctions() {}


    static Consumer<int[]> whenWrongRequest = r ->
            buildResponseFromState
                    .andThen(CommandLineResponseHandlerFunctions.wrongRequestMessage)
                    .andThen(print).accept(r);

    static Consumer<int[]> whenNextTurn = r ->
            buildResponseFromState
                    .andThen(print)
                    .accept(r);

    static Consumer<int[]> whenFinished = r ->
            buildResponseFromState
                    .andThen(CommandLineResponseHandlerFunctions.finalResponse)
                    .andThen(print)
                    .andThen(finishGame)
                    .accept(r);

    static Function<StringBuilder, StringBuilder> wrongRequestMessage = sb -> {
        sb.append(WRONG_INPUT_MSG).append("\n");
        return sb;
    };

    static Function<StringBuilder, StringBuilder> finalResponse = sb -> {
        sb.append(FINAL_MSG).append("\n");
        return sb;
    };


}
