package com.alibou.security.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppControllerAdvice {

    @ExceptionHandler(AppNotFoundException.class)
    public ResponseEntity<Response> handleException(AppNotFoundException e) {
        Response response = new Response(e.getMessage(), e.getErrorCode().getCode());
        return new ResponseEntity<>(response, ErrorCode.CERTIFICATE_NOT_FOUND.getStatus());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response> handleException(BadRequestException e) {
        Response response = new Response(e.getMessage(), e.getErrorCode().getCode());
        return new ResponseEntity<>(response, ErrorCode.BAD_REQUEST_ERROR.getStatus());
    }

}

