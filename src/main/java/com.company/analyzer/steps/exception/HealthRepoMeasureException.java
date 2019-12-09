package com.company.analyzer.steps.exception;

public class HealthRepoMeasureException extends Exception {
    public HealthRepoMeasureException() {
    }

    public HealthRepoMeasureException(String message) {
        super(message);
    }

    public HealthRepoMeasureException(String message, Throwable cause) {
        super(message, cause);
    }

    public HealthRepoMeasureException(Throwable cause) {
        super(cause);
    }

    public HealthRepoMeasureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
