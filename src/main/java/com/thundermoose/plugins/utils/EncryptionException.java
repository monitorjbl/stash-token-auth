package com.thundermoose.plugins.utils;

public class EncryptionException extends RuntimeException {
  public EncryptionException(String msg, Throwable t) {
    super(msg, t);
  }
}
