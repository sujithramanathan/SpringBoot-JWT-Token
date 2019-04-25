package com.reports.controller;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandlerAdvice {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handle(BadCredentialsException bce) {
        return genericHandle("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handle(IOException ie) {
        return genericHandle("Something went wrong, Please try again later ", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<String> handle(ParseException e) {

        return genericHandle("Unable to parse :: ".concat(e.getMessage()), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleUncaughtException(Exception ex, WebRequest request) {

        String message = "Something bad happened";
        return new ResponseEntity<Object>(message.concat(" :: ".concat(ex.getMessage())), HttpStatus.BAD_REQUEST);

    }

    private ResponseEntity<String> genericHandle(String msg, HttpStatus status) {
        String jsonMessage = "{\"message\":\"" + msg + "\"}";
        return new ResponseEntity<String>(jsonMessage, status);
    }
}
