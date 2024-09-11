package com.howtodoinjava.app.authentication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordHash {

  private static final byte[] salt = "794aa758-e97e-4be4-aabc-3e02df8c5594".getBytes();
  private static final MessageDigest md = messageDigest();

  public static String hash(String password) {
    md.update(salt);
    byte[] bytes = md.digest(password.getBytes());
    return Base64.getEncoder().encodeToString(bytes);
  }

  private static MessageDigest messageDigest() {
    try {
      return MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
    }
    return null;
  }
}
