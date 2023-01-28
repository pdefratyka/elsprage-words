package com.elsprage.words.exception;

public class WordRequestValidationException extends RuntimeException {
    public WordRequestValidationException(String message) {
        super(message);
    }
}
