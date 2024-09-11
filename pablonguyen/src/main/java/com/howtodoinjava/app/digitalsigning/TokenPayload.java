package com.howtodoinjava.app.digitalsigning;

public class TokenPayload {
  public String userId;
  public Integer expiresInSec = 3600;

  public TokenPayload() {
  }

  public TokenPayload(String userId) {
    this.userId = userId;
  }
}
