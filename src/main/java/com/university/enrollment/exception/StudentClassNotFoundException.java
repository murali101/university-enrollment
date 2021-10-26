package com.university.enrollment.exception;

public class StudentClassNotFoundException extends RuntimeException {

    public StudentClassNotFoundException() {
        super();
    }

    public StudentClassNotFoundException(String message) {
        super(message);
    }

    public StudentClassNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentClassNotFoundException(Throwable cause) {
        super(cause);
    }

    protected StudentClassNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
