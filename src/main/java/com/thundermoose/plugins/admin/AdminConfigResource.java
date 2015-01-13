package com.thundermoose.plugins.admin;

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

import com.atlassian.sal.api.user.UserManager;
import com.atlassian.sal.api.user.UserProfile;
import com.thundermoose.plugins.utils.KeyGenerator;

@Path("/admin")
public class AdminConfigResource {
  private final UserManager userManager;
  private final AdminConfigDao dao;

  public AdminConfigResource(UserManager userManager, AdminConfigDao dao) {
    this.userManager = userManager;
    this.dao = dao;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response get(@Context HttpServletRequest request) {
    UserProfile user = userManager.getRemoteUser();
    if (user == null || !userManager.isSystemAdmin(user.getUserKey())) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    return Response.ok(dao.getAdminConfig()).build();
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public Response put(final AdminConfig config, @Context HttpServletRequest request) {
    UserProfile user = userManager.getRemoteUser();
    if (user == null || !userManager.isSystemAdmin(user.getUserKey())) {
      return Response.status(Status.UNAUTHORIZED).build();
    }
    dao.setAdminConfig(config);
    return Response.noContent().build();
  }


}