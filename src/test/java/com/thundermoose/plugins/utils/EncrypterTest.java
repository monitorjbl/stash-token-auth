package com.thundermoose.plugins.utils;

import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.assertEquals;

public class EncrypterTest {

  @Test
  public void testEncrpytion() throws Exception {
    String key = new KeyGenerator().generateKey();
    Encrypter e = new Encrypter(Base64.getDecoder().decode(key));

    System.out.println(key);
    String str = "Ticket to ride";
    String enc = e.encrypt(str);
    String dec = e.decrypt(enc);
    System.out.println(str + " -> " + enc + " -> " + dec);

    assertEquals(str, dec);
  }
}
