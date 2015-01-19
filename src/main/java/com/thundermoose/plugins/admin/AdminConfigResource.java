package com.thundermoose.plugins.admin;

import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.sal.api.user.UserProfile;
import com.thundermoose.plugins.user.UserConfig;
import com.thundermoose.plugins.user.UserConfigDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Objects;

@Path("/admin")
public class AdminConfigResource {
  private final static Logger log = LoggerFactory.getLogger(AdminConfigResource.class);

  private final TransactionTemplate transactionTemplate;
  private final UserManager userManager;
  private final AdminConfigDao adminDao;
  private final UserConfigDao userDao;

  public AdminConfigResource(TransactionTemplate transactionTemplate, UserManager userManager, AdminConfigDao adminDao, UserConfigDao userDao) {
    this.transactionTemplate = transactionTemplate;
    this.userManager = userManager;
    this.adminDao = adminDao;
    this.userDao = userDao;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response get(@Context HttpServletRequest request) {
    UserProfile user = userManager.getRemoteUser();
    if (user == null || !userManager.isSystemAdmin(user.getUserKey())) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    return Response.ok(adminDao.getAdminConfig()).build();
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public Response put(final AdminConfig config, @Context HttpServletRequest request) {
    final UserProfile user = userManager.getRemoteUser();
    if (user == null || !userManager.isSystemAdmin(user.getUserKey())) {
      return Response.status(Status.UNAUTHORIZED).build();
    }

    transactionTemplate.execute(new TransactionCallback<Object>() {
      @Override
      public Object doInTransaction() {
        AdminConfig current = adminDao.getAdminConfig();

        //if the server secret changes, we need to invalidate all tokens
        if (!Objects.equals(current.getKey(), config.getKey())) {
          log.warn("Server secret was changed, removing all current tokens");
          for (UserConfig uc : userDao.getAllUserConfigs()) {
            uc.setToken(null);
            userDao.setUserConfig(uc.getUsername(), uc);
          }
        }

        adminDao.setAdminConfig(config);
        return null;
      }
    });

    return Response.noContent().build();
  }


}