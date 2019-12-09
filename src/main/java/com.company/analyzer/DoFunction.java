package com.company.analyzer;

/**
 * Accept an input then produce an output based on call back function
 */
public class DoFunction<Input> {
    public static <Input> Object apply(Input input, Execute callback) throws Exception {
        return callback.apply(input);
    }
}
