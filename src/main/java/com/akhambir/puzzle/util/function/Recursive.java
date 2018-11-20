package com.akhambir.puzzle.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Recursive<I> {

    private I f;

    public static <T, R> Function<T, R> function(BiFunction<T, Function<T, R>, R> f) {
        final Recursive<Function<T, R>> r = new Recursive<>();
        return r.f = t -> f.apply(t, r.f);
    }

    public static <T, U, R> BiFunction<T, U, R> biFunction(RecursiveBiFunction<T, U, R> f) {
        final Recursive<BiFunction<T, U, R>> r = new Recursive<>();
        return r.f = (t, u) -> f.apply(t, u, r.f);
    }
}
