package com.akhambir.puzzle.util.function;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.akhambir.puzzle.util.Constants.EMPTY_CELL;

public final class Functions {

    private Functions() {}

    public static Function<Integer, Function<int[], Integer>> findPos = r -> gs -> {
        int[] gsCopy = Arrays.copyOf(gs, gs.length);
        gsCopy[EMPTY_CELL] = r;
        return Functions.linearSearch.apply(gsCopy, 1);
    };

    public static BiFunction<int[], Integer, Integer> linearSearch =
            Recursive.biFunction((gs, i, self) -> gs.length > i
                    ? gs[EMPTY_CELL] != gs[i]
                        ? self.apply(gs, i + 1)
                        : i
                    : -1);

    public static ComposableFunction<int[], StringBuilder> buildResponseFromState = r -> {
        StringBuilder sb = new StringBuilder();
        sb
                .append(String.format("| %s | %s | %s | %s |", r[1], r[2], r[3], r[4])).append("\n")
                .append(String.format("| %s | %s | %s | %s |", r[5], r[6], r[7], r[8])).append("\n")
                .append(String.format("| %s | %s | %s | %s |", r[9], r[10], r[11], r[12])).append("\n")
                .append(String.format("| %s | %s | %s | %s |", r[13], r[14], r[15], r[16])).append("\n");

        return sb;
    };

    public static Consumer<StringBuilder> print = System.out::println;

    public static Consumer<Object> finishGame = r -> {
        System.exit(0);
    };
}
