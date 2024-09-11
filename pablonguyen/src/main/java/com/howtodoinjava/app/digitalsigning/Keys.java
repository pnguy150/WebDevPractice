package com.howtodoinjava.app.digitalsigning;

import java.security.*;

public class Keys {
  private static final KeyPair keyPair = generateKeyPair();

  public static PublicKey getPublicKey() {
    return keyPair.getPublic();
  }

  public static PrivateKey getPrivateKey() {
    return keyPair.getPrivate();
  }
  private static KeyPair generateKeyPair() {
    try {
      KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");
      keyPairGen.initialize(2048);
      KeyPair keyPair = keyPairGen.generateKeyPair();
      return keyPair;
    } catch (NoSuchAlgorithmException e) {
      return null;
    }
  }
}
