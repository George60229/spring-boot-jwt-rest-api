package com.alibou.security.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    CERTIFICATE_NOT_FOUND(40401, "Certificate is not found", HttpStatus.NOT_FOUND),
    TAG_NOT_FOUND(40402, "Tag is not found", HttpStatus.NOT_FOUND),

    USER_NOT_FOUND(40403, "user is not found", HttpStatus.NOT_FOUND),
    BAD_REQUEST_ERROR(40001, "Request is failed", HttpStatus.BAD_REQUEST);
    private final int errorCode;
    private final String errorMessage;
    private final HttpStatus status;

    public HttpStatus getStatus() {
        return status;
    }

    ErrorCode(int code, String message, HttpStatus status) {
        this.errorCode = code;
        this.errorMessage = message;
        this.status = status;
    }

    public int getCode() {
        return errorCode;
    }

    public String getMessage() {
        return errorMessage;
    }
}
