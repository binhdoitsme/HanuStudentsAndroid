package com.hanu.students.base;

import androidx.arch.core.util.Function;

public abstract class JSONStringMapper<T> extends Converter<String, T> {
    public JSONStringMapper(Function<String, T> forwardConverter, Function<T, String> backwardConverter) {
        super(forwardConverter, backwardConverter);
    }
}
