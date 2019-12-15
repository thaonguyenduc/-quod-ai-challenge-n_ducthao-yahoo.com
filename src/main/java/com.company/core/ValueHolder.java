package com.company.core;

public class ValueHolder<T extends Number> {
    private T value;

    public ValueHolder(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
