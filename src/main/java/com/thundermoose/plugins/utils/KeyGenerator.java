package com.thundermoose.plugins.utils;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class KeyGenerator {
  public String generateKey() {
    try {
      return new String(Base64.getEncoder().encode(javax.crypto.KeyGenerator.getInstance("AES").generateKey().getEncoded()));
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }


}
