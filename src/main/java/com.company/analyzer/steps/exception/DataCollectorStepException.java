package com.company.analyzer.steps.exception;

public class DataCollectorStepException extends Exception {
    public DataCollectorStepException() {
    }

    public DataCollectorStepException(String message) {
        super(message);
    }

    public DataCollectorStepException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataCollectorStepException(Throwable cause) {
        super(cause);
    }

    public DataCollectorStepException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
