package com.company.analyzer.statistics.exception;

public class AveragePullRequestMergedException extends Exception {
    public AveragePullRequestMergedException() {
    }

    public AveragePullRequestMergedException(String message) {
        super(message);
    }

    public AveragePullRequestMergedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AveragePullRequestMergedException(Throwable cause) {
        super(cause);
    }

    public AveragePullRequestMergedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
