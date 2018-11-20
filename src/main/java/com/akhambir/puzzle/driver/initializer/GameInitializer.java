package com.akhambir.puzzle.driver.initializer;

import static com.akhambir.puzzle.driver.initializer.GameInitializerFunctions.checkSolvability;
import static com.akhambir.puzzle.driver.initializer.GameInitializerFunctions.respondAndContinue;
import static com.akhambir.puzzle.driver.initializer.GameInitializerFunctions.shuffle;

public class GameInitializer {

    public int[] initialize() {
        int[] initialGameState = {-1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 15};

        return shuffle
                .andThen(checkSolvability)
                .andThen(respondAndContinue)
                .apply(initialGameState);
    }
}
