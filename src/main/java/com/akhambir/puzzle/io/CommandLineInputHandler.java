package com.akhambir.puzzle.io;

import com.akhambir.puzzle.io.interfaces.InputHandler;
import com.akhambir.puzzle.processor.interfaces.GameProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.akhambir.puzzle.io.CommandLineInputHandlerFunctions.handleNextTurnInput;
import static com.akhambir.puzzle.io.CommandLineInputHandlerFunctions.termination;
import static com.akhambir.puzzle.util.Constants.QUIT;

public class CommandLineInputHandler implements InputHandler {

    private final GameProcessor gameProcessor;
    private final Scanner sc;

    private final static Map<String, Function<String, Consumer<GameProcessor>>> functionsMap = new HashMap<>();

    static {
        functionsMap.put(QUIT, termination);
    }

    public CommandLineInputHandler(GameProcessor gameProcessor, Scanner sc) {
        this.gameProcessor = gameProcessor;
        this.sc = sc;
    }

    public void handle() {
        String input = sc.next().toLowerCase();
        functionsMap.getOrDefault(input, handleNextTurnInput)
                .apply(input)
                .accept(gameProcessor);
    }
}
