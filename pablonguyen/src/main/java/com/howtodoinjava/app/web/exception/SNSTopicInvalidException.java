package com.howtodoinjava.app.web.exception;

public class SNSTopicInvalidException extends RuntimeException{
    private String message;
    public SNSTopicInvalidException(String message){
        this.message = message;
    }
}
