package com.traineemanagement.gateway.exceptions;


public class NoAuthorizationHeaderException extends RuntimeException {
    public NoAuthorizationHeaderException(String message) {
        super(message);
    }
}
