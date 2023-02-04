package com.elsprage.words.web.exception;

import com.elsprage.words.common.constants.ExceptionConstants;
import com.elsprage.words.exception.WordException;
import com.elsprage.words.exception.WordRequestValidationException;
import com.elsprage.words.model.response.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(WordRequestValidationException.class)
    public ResponseEntity<ExceptionResponse> handleWordValidationException(WordRequestValidationException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(),
                HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(WordException.class)
    public ResponseEntity<ExceptionResponse> handleWordException(WordException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(),
                ex.getHttpStatus().value());
        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleInternalServerError(Exception ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ExceptionConstants.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
