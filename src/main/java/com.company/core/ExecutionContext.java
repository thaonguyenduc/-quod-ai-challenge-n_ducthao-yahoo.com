package com.company.core;

import java.util.ArrayList;
import java.util.List;

public class ExecutionContext {
    private final List<String> dateRange;

    public ExecutionContext(List<String> dateRanges) {
        this.dateRange = new ArrayList<>(dateRanges);
    }

    public List<String> getDateRange() {
        return dateRange;
    }
}
