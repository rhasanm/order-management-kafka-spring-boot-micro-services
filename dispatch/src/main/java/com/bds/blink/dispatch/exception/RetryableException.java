package com.bds.blink.dispatch.exception;

public class RetryableException extends RuntimeException {
    public RetryableException(String message) {
        super(message);
    }

    public RetryableException(Exception exception) {
        super(exception);
    }
}
