package com.howtodoinjava.app.web.exception;

import java.io.IOException;

public class ForbiddenException extends RuntimeException {
  public ForbiddenException(String message) {
    super(message);
  }
}