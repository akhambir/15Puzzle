package com.akhambir.puzzle.driver;

import com.akhambir.puzzle.driver.interfaces.GameDriver;
import com.akhambir.puzzle.io.interfaces.InputHandler;

public class GameDriverImpl implements GameDriver {

    private final InputHandler inputHandler;

    public GameDriverImpl(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    @Override
    public void start() {
        while (true) {
            inputHandler.handle();
        }
    }
}
