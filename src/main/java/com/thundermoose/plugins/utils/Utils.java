package com.thundermoose.plugins.utils;

import com.atlassian.sal.api.auth.LoginUriProvider;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

public class Utils {
  private final LoginUriProvider loginUriProvider;

  public Utils(LoginUriProvider loginUriProvider) {
    this.loginUriProvider = loginUriProvider;
  }

  public void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.sendRedirect(loginUriProvider.getLoginUri(getUri(request)).toASCIIString());
  }

  public URI getUri(HttpServletRequest request) {
    StringBuffer builder = request.getRequestURL();
    if (request.getQueryString() != null) {
      builder.append("?");
      builder.append(request.getQueryString());
    }
    return URI.create(builder.toString());
  }

  /**
   * Generates an unencrypted token string with 4 parts separated by colons
   * <ul>
   * <li>username</li>
   * <li>created date</li>
   * <li>expiration date</li>
   * <li>random padding string</li>
   * </ul>
   *
   * @param username
   * @param ttl
   * @return
   */
  public String generateTokenForUser(String username, Integer ttl) {
    return username + ":" + System.currentTimeMillis() + ":" + DateTime.now().plusDays(ttl).getMillis() + ":" + UUID.randomUUID().toString();
  }

  public static String createRegexFromGlob(String line) {
    if (line.endsWith("**")) {
      String chopped = line.substring(0, line.length() - 2);
      if (!chopped.endsWith("/")) {
        throw new IllegalArgumentException("Glob operator must be preceeded by a '/' character");
      }
      String single = regexWildcard(escapeRegexCharacters(chopped.substring(0, chopped.length() - 1)));
      return regexWildcard(escapeRegexCharacters(chopped)) + ".*|" + single;
    } else {
      //can't support globs that aren't at the end, make sure there aren't any
      if (line.replaceAll("(\\*\\*)", "").length() != line.length()) {
        throw new IllegalArgumentException("Glob operator (**) can only be at the end of a path");
      }
      return regexWildcard(escapeRegexCharacters(line));
    }
  }

  public static String escapeRegexCharacters(String line) {
    return line.replaceAll("\\.", "\\\\.").replaceAll("\\+", "\\\\+");
  }

  public static String regexWildcard(String line) {
    return line.replaceAll("(\\*)", "[^/]*");
  }
}
