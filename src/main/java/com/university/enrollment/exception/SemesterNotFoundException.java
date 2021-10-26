package com.university.enrollment.exception;

public class SemesterNotFoundException extends RuntimeException {
    public SemesterNotFoundException() {
        super();
    }

    public SemesterNotFoundException(String message) {
        super(message);
    }

    public SemesterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SemesterNotFoundException(Throwable cause) {
        super(cause);
    }

    protected SemesterNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
