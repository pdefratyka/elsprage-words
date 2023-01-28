package com.elsprage.words.exception;

public class LanguageDoesNotExists extends RuntimeException {

    public LanguageDoesNotExists(Long id) {
        super("Language with id: " + id + " does not exists.");
    }
}
