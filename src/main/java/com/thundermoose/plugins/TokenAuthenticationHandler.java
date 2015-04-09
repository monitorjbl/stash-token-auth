package com.thundermoose.plugins;

import com.atlassian.stash.auth.HttpAuthenticationContext;
import com.atlassian.stash.auth.HttpAuthenticationHandler;
import com.atlassian.stash.i18n.I18nKey;
import com.atlassian.stash.i18n.I18nService;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;
import com.thundermoose.plugins.admin.AdminConfig;
import com.thundermoose.plugins.admin.AdminConfigDao;
import com.thundermoose.plugins.paths.PathMatcher;
import com.thundermoose.plugins.user.UserConfig;
import com.thundermoose.plugins.user.UserConfigDao;
import com.thundermoose.plugins.utils.Encrypter;
import com.thundermoose.plugins.utils.EncryptionException;
import com.thundermoose.plugins.utils.Utils;
import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class TokenAuthenticationHandler implements HttpAuthenticationHandler {
  private static final Logger log = LoggerFactory.getLogger(TokenAuthenticationHandler.class);
  public static final String USER_HEADER = "X-Auth-User";
  public static final String TOKEN_HEADER = "X-Auth-Token";

  private final UserService userService;
  private final I18nService i18nService;
  private final Utils utils;
  private final UserConfigDao userDao;
  private final AdminConfigDao adminDao;

  public TokenAuthenticationHandler(UserService userService, I18nService i18nService, Utils utils,
                                    UserConfigDao userDao, AdminConfigDao adminDao) {
    this.userService = userService;
    this.i18nService = i18nService;
    this.utils = utils;
    this.userDao = userDao;
    this.adminDao = adminDao;
  }

  @Override
  public void validateAuthentication(HttpAuthenticationContext httpAuthenticationContext) {

  }

  @Nullable
  @Override
  public StashUser authenticate(HttpAuthenticationContext ctx) {
    HttpServletRequest request = ctx.getRequest();
    String username = request.getHeader(USER_HEADER);
    String token = request.getHeader(TOKEN_HEADER);
    String path = request.getRequestURI().replaceFirst(request.getContextPath(), "");

    if (isNotEmpty(username) && isNotEmpty(token) && path.startsWith("/rest/")) {
      if (isTokenValid(path, username, token)) {
        return userService.getUserByName(username);
      }
    }

    return null;
  }

  boolean isTokenValid(String path, String username, String token) {
    try {
      AdminConfig config = adminDao.getAdminConfig();
      if (!config.getEnabled()) {
        return false;
      }

      Encrypter encrypter = new Encrypter(Base64.decodeBase64(config.getKey()));
      String unencrypted = encrypter.decrypt(token);
      String[] split = unencrypted.split(":");
      if (split.length != 4) {
        //not a valid token
        return false;
      }

      Integer ttl = adminDao.getAdminConfig().getTtl();
      DateTime expiry = new DateTime(Long.parseLong(split[1])).plusDays(ttl);
      if (Objects.equals(split[0], username) && (ttl <= 0 || DateTime.now().isBefore(expiry))) {
        //token is valid, see if the path is allowed token access by admin
        return new PathMatcher(
            config.getAdminPaths(),
            config.getProjectPaths(),
            config.getRepoPaths()
        ).pathAllowed(path) && Objects.equals(userDao.getUserConfig(username).getToken(), token);
      } else if (Objects.equals(split[0], username) && DateTime.now().isAfter(expiry)) {
        //token is expired, generate a new one
        userDao.setUserConfig(username, new UserConfig(encrypter.encrypt(utils.generateTokenForUser(username, config.getTtl()))));
      }
    } catch (EncryptionException e) {
      log.debug("Could not decrypt provided token", e);
      throw new TokenAuthenticationException(i18nService.getKeyedText(new I18nKey("auth.exception.message")));
    }
    return false;
  }

}
