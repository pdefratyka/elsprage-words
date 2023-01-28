package com.elsprage.words.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ExceptionResponse {
    private LocalDateTime time;
    private String message;
    private int httpStatus;

    public ExceptionResponse(String message, int httpStatus) {
        this.time = LocalDateTime.now();
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
