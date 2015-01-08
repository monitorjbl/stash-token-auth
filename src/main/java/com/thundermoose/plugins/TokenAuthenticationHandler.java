package com.thundermoose.plugins;

import com.atlassian.stash.auth.HttpAuthenticationContext;
import com.atlassian.stash.auth.HttpAuthenticationHandler;
import com.atlassian.stash.i18n.I18nService;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class TokenAuthenticationHandler implements HttpAuthenticationHandler {
  private static final Logger log = LoggerFactory.getLogger(TokenAuthenticationHandler.class);
  public static final String KEY_CONTAINER_AUTH_NAME = "auth.container.remote-user";
  public static final String HEADER_NAME = "X-Auth-Token";

  private final I18nService i18nService;
  private final UserService userService;

  public TokenAuthenticationHandler(I18nService i18nService, UserService userService) {
    this.i18nService = i18nService;
    this.userService = userService;
  }


  @Override
  public void validateAuthentication(HttpAuthenticationContext httpAuthenticationContext) {

  }

  @Nullable
  @Override
  public StashUser authenticate(HttpAuthenticationContext httpAuthenticationContext) {
    HttpServletRequest request = httpAuthenticationContext.getRequest();
    String remoteUser = request.getHeader(HEADER_NAME);

    if (isNotEmpty(remoteUser)) {
      log.warn("Token auth for user [" + remoteUser + "] allowed for [" + request.getRequestURI() + "]");
      return userService.getUserByName(remoteUser);
    }

    return null;
  }

}
