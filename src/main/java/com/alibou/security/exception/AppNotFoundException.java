package com.alibou.security.exception;

public class AppNotFoundException extends IllegalArgumentException {
    ErrorCode code;

    public AppNotFoundException(String s, ErrorCode code) {
        super(s);
        this.code = code;
    }

    public ErrorCode getErrorCode() {
        return code;
    }
}
