package com.akhambir.puzzle.util.function;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

@FunctionalInterface
public interface ComposableFunction<T, R> extends Function<T, R> {

    default <V> ComposableFunction<T, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    default Consumer<T> andThen(Consumer<R> after) {
        Objects.requireNonNull(after);
        return (T t) -> {after.accept(apply(t));};
    }
}