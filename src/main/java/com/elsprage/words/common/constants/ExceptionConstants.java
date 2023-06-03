package com.elsprage.words.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionConstants {
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    public static final String BAD_REQUEST = "Bad Request";

    public static final String TIME = "time";
    public static final String HTTP_STATUS = "httpStatus";
}
