package com.elsprage.words.web.exception;

import com.elsprage.words.common.constants.ExceptionConstants;
import com.elsprage.words.exception.ExceptionCode;
import com.elsprage.words.exception.PacketException;
import com.elsprage.words.exception.WordException;
import com.elsprage.words.model.response.ExceptionResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class ControllerAdvice {

    @ExceptionHandler(WordException.WordAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> handleWordAlreadyExistException(Exception ex) {
        ExceptionResponse response = new ExceptionResponse(ExceptionConstants.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionCode.WORD_ALREADY_EXIST_CODE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(Exception ex) {
        ExceptionResponse response = new ExceptionResponse(ExceptionConstants.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(), ExceptionCode.WORD_ALREADY_EXIST_CODE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(WordException.class)
    public ResponseEntity<ExceptionResponse> handleWordException(WordException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(),
                ex.getHttpStatus().value());
        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }

    @ExceptionHandler(PacketException.class)
    public ResponseEntity<ExceptionResponse> handlePacketException(PacketException ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(),
                ex.getHttpStatus().value());
        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(Exception ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ExceptionConstants.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleInternalServerError(Exception ex) {
        log.error(ex.getMessage(), ex);
        ExceptionResponse response = new ExceptionResponse(ExceptionConstants.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
