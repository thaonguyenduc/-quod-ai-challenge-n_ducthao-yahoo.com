package com.company.analyzer.statistics.exception;

public class AverageTimePullRequestMergeException extends Exception {
    public AverageTimePullRequestMergeException() {
    }

    public AverageTimePullRequestMergeException(String message) {
        super(message);
    }

    public AverageTimePullRequestMergeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AverageTimePullRequestMergeException(Throwable cause) {
        super(cause);
    }

    public AverageTimePullRequestMergeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
