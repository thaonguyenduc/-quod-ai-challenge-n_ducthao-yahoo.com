package com.company.analyzer.statistics.exception;

public class AverageCommitPerDayException extends Exception {
    public AverageCommitPerDayException() {
    }

    public AverageCommitPerDayException(String message) {
        super(message);
    }

    public AverageCommitPerDayException(String message, Throwable cause) {
        super(message, cause);
    }

    public AverageCommitPerDayException(Throwable cause) {
        super(cause);
    }

    public AverageCommitPerDayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
