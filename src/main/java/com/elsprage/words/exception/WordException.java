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

    public WordException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
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

    public static class WordAlreadyExistException extends WordException {
        public WordAlreadyExistException(Throwable cause) {
            super("Word already exist", HttpStatus.BAD_REQUEST, cause);
        }
    }
}
