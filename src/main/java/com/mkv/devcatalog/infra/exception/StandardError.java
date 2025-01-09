package com.mkv.devcatalog.infra.exception;

public class StandardError extends RuntimeException{
    public StandardError(String message) {
        super(message);
    }
}
