package com.thundermoose.plugins.utils;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UtilsTest {

  @Test
  public void testCreateRegexFromGlob_globAtEnd() {
    String regex = Utils.createRegexFromGlob("/rest/api/1.0/admin/users/**");
    Pattern p = Pattern.compile(regex);
    assertTrue(p.matcher("/rest/api/1.0/admin/users").matches());
    assertTrue(p.matcher("/rest/api/1.0/admin/users/add-group").matches());
  }

  @Test
  public void testCreateRegexFromGlob_wildcardInMiddle() {
    String regex = Utils.createRegexFromGlob("/rest/api/1.0/projects/*/permissions");
    System.out.println(regex);
    Pattern p = Pattern.compile(regex);
    assertTrue(p.matcher("/rest/api/1.0/projects/ASDF/permissions").matches());
    assertFalse(p.matcher("/rest/api/1.0/projects/ASDF/fake/permissions").matches());
  }
}
