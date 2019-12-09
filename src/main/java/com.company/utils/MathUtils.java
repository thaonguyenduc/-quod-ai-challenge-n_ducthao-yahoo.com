package com.company.utils;

import java.util.Arrays;


public class MathUtils {

    public static Double sum(Double... averages) {
        return Arrays.stream(averages).mapToDouble(average -> average).sum();
    }

}
