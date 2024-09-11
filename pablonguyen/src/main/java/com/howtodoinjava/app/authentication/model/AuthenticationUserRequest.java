package com.howtodoinjava.app.authentication.model;

public class AuthenticationUserRequest {
  public String userId;
  public String password;

  public AuthenticationUserRequest() {
  }

    AuthenticationUserRequest(String userId, String password) {
    this.userId = userId;
    this.password = password;
  }
}
