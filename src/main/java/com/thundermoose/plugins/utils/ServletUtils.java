package com.thundermoose.plugins.utils;

import com.atlassian.sal.api.auth.LoginUriProvider;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

public class ServletUtils {
  private final LoginUriProvider loginUriProvider;

  public ServletUtils(LoginUriProvider loginUriProvider) {
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
}
