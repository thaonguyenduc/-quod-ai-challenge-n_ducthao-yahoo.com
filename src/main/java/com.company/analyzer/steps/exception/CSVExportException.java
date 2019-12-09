package com.company.analyzer.steps.exception;

public class CSVExportException extends Exception {
    public CSVExportException() {
    }

    public CSVExportException(String message) {
        super(message);
    }

    public CSVExportException(String message, Throwable cause) {
        super(message, cause);
    }

    public CSVExportException(Throwable cause) {
        super(cause);
    }

    public CSVExportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
