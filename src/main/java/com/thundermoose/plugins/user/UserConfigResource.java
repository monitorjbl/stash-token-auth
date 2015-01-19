package com.thundermoose.plugins.user;

import com.atlassian.sal.api.user.UserManager;
import com.atlassian.sal.api.user.UserProfile;
import com.thundermoose.plugins.admin.AdminConfigDao;
import com.thundermoose.plugins.utils.Encrypter;
import com.thundermoose.plugins.utils.Utils;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserConfigResource {
  private final UserManager userManager;
  private final AdminConfigDao adminDao;
  private final UserConfigDao userDao;
  private final Utils utils;

  public UserConfigResource(UserManager userManager, AdminConfigDao adminDao, UserConfigDao userDao, Utils utils) {
    this.userManager = userManager;
    this.adminDao = adminDao;
    this.userDao = userDao;
    this.utils = utils;
  }

  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getConfig(@Context HttpServletRequest request) {
    UserProfile user = userManager.getRemoteUser();
    String username = user.getUsername();
    UserConfig config = userDao.getUserConfig(username);
    if (config.getToken() == null) {
      config.setToken(generateEncryptedToken(username));
      userDao.setUserConfig(username, config);
    }
    return Response.ok(config).build();
  }

  @GET
  @Path("/regenerate-token")
  @Produces(MediaType.APPLICATION_JSON)
  public Response regenerateToken(@Context HttpServletRequest request) {
    UserProfile user = userManager.getRemoteUser();
    String username = user.getUsername();
    userDao.setUserConfig(username, new UserConfig(generateEncryptedToken(username)));
    return Response.ok().build();
  }

  private String generateEncryptedToken(String username) {
    Encrypter encrypter = new Encrypter(Base64.decodeBase64(adminDao.getAdminConfig().getKey()));
    return encrypter.encrypt(utils.generateTokenForUser(username, adminDao.getAdminConfig().getTtl()));
  }


}