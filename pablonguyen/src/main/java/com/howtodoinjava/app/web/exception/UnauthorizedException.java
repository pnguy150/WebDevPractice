package com.howtodoinjava.app.web.exception;

import java.io.IOException;

public class UnauthorizedException extends RuntimeException {
  public UnauthorizedException(String message) {
    super(message);
  }
}
