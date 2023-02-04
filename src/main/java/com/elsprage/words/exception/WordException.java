package com.elsprage.words.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WordException extends RuntimeException {

    private final HttpStatus httpStatus;

    public WordException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public static class WordNotFound extends WordException {
        public WordNotFound(String message) {
            super(message, HttpStatus.BAD_REQUEST);
        }
    }

    public static class WrongUserId extends WordException {
        public WrongUserId(String message) {
            super(message, HttpStatus.FORBIDDEN);
        }
    }
}
