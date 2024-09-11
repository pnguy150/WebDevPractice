package com.howtodoinjava.app.web.exception;

import java.io.IOException;

public class ServiceUnavailableException extends RuntimeException {
  public ServiceUnavailableException(String message) {
    super(message);
  }
}