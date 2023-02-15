package com.alibou.security.exception;

public class BadRequestException extends IllegalArgumentException {
    private final ErrorCode errorCode;

    public BadRequestException(String s, ErrorCode code) {
        super(s);
        this.errorCode = code;

    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
