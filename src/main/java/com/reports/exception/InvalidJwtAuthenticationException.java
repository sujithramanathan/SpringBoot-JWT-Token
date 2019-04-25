package com.reports.exception;

public class InvalidJwtAuthenticationException extends RuntimeException {

    private static final long serialVersionUID = 1339926383590813449L;

    public InvalidJwtAuthenticationException(String msg) {
        super(msg);
    }

}
