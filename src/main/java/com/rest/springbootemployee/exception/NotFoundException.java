package com.rest.springbootemployee.exception;

public class NotFoundException extends RuntimeException{
    @Override
    public String getMessage() {
        return "employee not found";
    }
}
