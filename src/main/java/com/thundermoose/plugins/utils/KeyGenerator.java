package com.thundermoose.plugins.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.NoSuchAlgorithmException;

public class KeyGenerator {
  public String generateKey() {
    try {
      return new String(Base64.encodeBase64(javax.crypto.KeyGenerator.getInstance("AES").generateKey().getEncoded()));
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }


}
