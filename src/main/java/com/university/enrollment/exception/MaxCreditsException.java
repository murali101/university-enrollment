package com.university.enrollment.exception;

public class MaxCreditsException extends RuntimeException {
    public MaxCreditsException() {
        super();
    }

    public MaxCreditsException(String message) {
        super(message);
    }

    public MaxCreditsException(String message, Throwable cause) {
        super(message, cause);
    }

    public MaxCreditsException(Throwable cause) {
        super(cause);
    }

    protected MaxCreditsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
