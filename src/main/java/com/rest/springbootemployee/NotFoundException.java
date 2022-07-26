package com.rest.springbootemployee;

public class NotFoundException extends RuntimeException{
    @Override
    public String getMessage() {
        return "employee not found";
    }
}
