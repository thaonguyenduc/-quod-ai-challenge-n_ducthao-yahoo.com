package com.company.analyzer.steps;

public interface Step<In, Out> {
    static <I, O> Step<I, O> of(Step<I, O> source) {
        return source;
    }

    Out execute(In value) throws Exception;

    default <R> Step<In, R> pipe(Step<Out, R> source) {
        return value -> source.execute(execute(value));
    }
}
