package com.thundermoose.plugins.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Encrypter {
  private Cipher ecipher;
  private Cipher dcipher;

  private SecretKey secretKey;

  public Encrypter(byte[] key) {
    try {
      secretKey = new SecretKeySpec(key, "AES");
      ecipher = Cipher.getInstance("AES");
      dcipher = Cipher.getInstance("AES");
      ecipher.init(Cipher.ENCRYPT_MODE, secretKey);
      dcipher.init(Cipher.DECRYPT_MODE, secretKey);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
      throw new RuntimeException(e);
    }
  }

  public String encrypt(String value) {
    try {
      byte[] utf8 = value.getBytes("UTF8");
      byte[] enc = ecipher.doFinal(utf8);
      return new String(Base64.encodeBase64(enc));
    } catch (UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException e) {
      throw new RuntimeException(e);
    }
  }

  public String decrypt(String value) {
    try {
      byte[] dec = Base64.decodeBase64(value.getBytes());
      byte[] utf8 = dcipher.doFinal(dec);
      return new String(utf8, "UTF8");
    } catch (UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException e) {
      throw new RuntimeException(e);
    }
  }
}
