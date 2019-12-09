package com.company.analyzer.statistics.exception;

public class IssuesRemainedOpenException extends Exception {
    public IssuesRemainedOpenException() {
    }

    public IssuesRemainedOpenException(String message) {
        super(message);
    }

    public IssuesRemainedOpenException(String message, Throwable cause) {
        super(message, cause);
    }

    public IssuesRemainedOpenException(Throwable cause) {
        super(cause);
    }

    public IssuesRemainedOpenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
