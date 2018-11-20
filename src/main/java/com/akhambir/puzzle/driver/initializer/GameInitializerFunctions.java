package com.akhambir.puzzle.driver.initializer;

import com.akhambir.puzzle.util.function.Recursive;

import java.util.Arrays;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static com.akhambir.puzzle.util.Constants.EMPTY_CELL;
import static com.akhambir.puzzle.util.Constants.FIRST_TILE;
import static com.akhambir.puzzle.util.Constants.SIDE_LENGTH;
import static com.akhambir.puzzle.util.function.Functions.buildResponseFromState;
import static com.akhambir.puzzle.util.function.Functions.findPos;

final class GameInitializerFunctions {

    private static final Random random = new Random();

    static Function<int[], int[]> checkSolvability = Recursive.function((gs, self) -> {
        boolean isSolvable = GameInitializerFunctions.countInversionsForWholeGameState
                .andThen(GameInitializerFunctions.checkInversionsAndLineOfEmptyPos)
                .apply(gs, FIRST_TILE)
                .apply(gs);

        if (isSolvable) {
            return gs;
        } else {
            return self.apply(GameInitializerFunctions.shuffle.apply(gs));
        }
    });

    static Function<Integer, Function<int[], Boolean>> checkInversionsAndLineOfEmptyPos =
            inv -> gs -> findPos.apply(EMPTY_CELL)
                    .andThen(GameInitializerFunctions.defineLineOfPosFromTheBottom)
                    .andThen(GameInitializerFunctions.compareInversionsToLineNumber)
                    .apply(gs)
                    .apply(inv);

    static UnaryOperator<Integer> defineLineOfPosFromTheBottom = p -> SIDE_LENGTH - (p - 1) / SIDE_LENGTH;

    static Function<Integer, Function<Integer, Boolean>> compareInversionsToLineNumber =
            ln -> inv -> (ln % 2 != 0) == (inv % 2 == 0);

    static BiFunction<int[], Integer, Integer> countInversionsForWholeGameState = Recursive.biFunction((gs, i, self) -> {
        int[] gsCopy = Arrays.copyOf(gs, gs.length);
        gsCopy[EMPTY_CELL] = i;
        int result = 0;

        if (gs.length > i + 1) {
            result = GameInitializerFunctions.countInversionsForSingleEntry.apply(gsCopy, i + 1);
            result += self.apply(gs, i + 1);
        }

        return result;
    });

    static BiFunction<int[], Integer, Integer> countInversionsForSingleEntry = Recursive.biFunction((gs, i, self) -> {
        int result = 0;
        if (gs.length > i) {
            if (gs[gs[EMPTY_CELL]] > gs[i] && gs[i] > 0) {
                result = self.apply(gs, i + 1) + 1;
            } else {
                result = self.apply(gs, i + 1);
            }
        }
        return result;
    });

    static UnaryOperator<int[]> shuffle = gs -> {
        int[] gsCopy = Arrays.copyOf(gs, gs.length);
        return GameInitializerFunctions.shuffle2.apply(gsCopy, FIRST_TILE);
    };

    static BiFunction<int[], Integer, int[]> shuffle2 = Recursive.biFunction((gs, i, self) -> {
        if (gs.length > i) {
            int randomInt = random.nextInt(i);
            int verifiedRandom = randomInt == 0 ? randomInt + 1 : randomInt;
            int swap = gs[verifiedRandom];
            gs[verifiedRandom] = gs[i];
            gs[i] = swap;
            return self.apply(gs, i + 1);
        } else {
            return gs;
        }
    });

    static UnaryOperator<int[]> respondAndContinue = r -> {
        StringBuilder response = buildResponseFromState.apply(r);
        System.out.println(response);
        return r;
    };
}
