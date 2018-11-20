package com.akhambir.puzzle.driver.initializer;

import static com.akhambir.puzzle.driver.initializer.GameInitializerFunctions.checkSolvability;
import static com.akhambir.puzzle.driver.initializer.GameInitializerFunctions.respondAndContinue;
import static com.akhambir.puzzle.driver.initializer.GameInitializerFunctions.shuffle;

public class GameInitializer {

    public int[] initialize(int[] initialGameState) {
        return shuffle
                .andThen(checkSolvability)
                .andThen(respondAndContinue)
                .apply(initialGameState);
    }
}
