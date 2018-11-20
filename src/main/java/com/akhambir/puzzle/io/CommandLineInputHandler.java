package com.akhambir.puzzle.io;

import com.akhambir.puzzle.driver.Request;
import com.akhambir.puzzle.io.interfaces.InputHandler;
import com.akhambir.puzzle.processor.interfaces.GameProcessor;

import java.util.Scanner;

import static com.akhambir.puzzle.io.CommandLineInputHandlerFunctions.parseInput;
import static com.akhambir.puzzle.io.CommandLineInputHandlerFunctions.processGameRequest;
import static com.akhambir.puzzle.io.CommandLineInputHandlerFunctions.validateInput;

public class CommandLineInputHandler implements InputHandler {

    private final GameProcessor gameProcessor;
    private final Scanner sc;


    public CommandLineInputHandler(GameProcessor gameProcessor, Scanner sc) {
        this.gameProcessor = gameProcessor;
        this.sc = sc;
    }

    public void handle() {
        parseInput
                .andThen(validateInput)
                .andThen(Request::of)
                .andThen(processGameRequest)
                .apply(sc.next())
                .accept(gameProcessor);
    }
}
