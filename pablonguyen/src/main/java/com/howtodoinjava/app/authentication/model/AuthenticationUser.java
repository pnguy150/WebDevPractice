package com.howtodoinjava.app.authentication.model;

public class AuthenticationUser {
  public String userId;
  public String hashedPassword;

  public AuthenticationUser() {
  }

  public AuthenticationUser(String userId, String hashedPassword) {
    this.userId = userId;
    this.hashedPassword = hashedPassword;
  }
}
