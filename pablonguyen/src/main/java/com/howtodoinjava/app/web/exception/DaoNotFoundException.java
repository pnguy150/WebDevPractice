package com.howtodoinjava.app.web.exception;

public class DaoNotFoundException extends RuntimeException{
    public String message;

    public DaoNotFoundException(String message){
        this.message = message;
    }
}
