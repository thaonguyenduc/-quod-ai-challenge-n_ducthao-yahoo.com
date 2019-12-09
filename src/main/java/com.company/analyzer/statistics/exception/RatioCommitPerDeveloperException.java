package com.company.analyzer.statistics.exception;

public class RatioCommitPerDeveloperException extends Exception {
    public RatioCommitPerDeveloperException() {
    }

    public RatioCommitPerDeveloperException(String message) {
        super(message);
    }

    public RatioCommitPerDeveloperException(String message, Throwable cause) {
        super(message, cause);
    }

    public RatioCommitPerDeveloperException(Throwable cause) {
        super(cause);
    }

    public RatioCommitPerDeveloperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
