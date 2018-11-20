package com.akhambir.puzzle;

import com.akhambir.puzzle.driver.interfaces.GameDriver;

import static com.akhambir.puzzle.util.Factory.getCommandLineInputHandler;
import static com.akhambir.puzzle.util.Factory.getCommandLineResponseHandler;
import static com.akhambir.puzzle.util.Factory.getGameDriverIml;
import static com.akhambir.puzzle.util.Factory.getGameProcessorImpl;

public class GameLauncher {


    public static void main(String[] args) {
        GameDriver gameDriver =
                getGameDriverIml(getCommandLineInputHandler(getGameProcessorImpl(getCommandLineResponseHandler())));

        gameDriver.start();
    }
}
