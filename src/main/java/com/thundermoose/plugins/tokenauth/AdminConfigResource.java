package com.thundermoose.plugins.tokenauth;

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

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.sal.api.user.UserProfile;
import org.apache.commons.lang3.BooleanUtils;

@Path("/")
public class AdminConfigResource {
  private final UserManager userManager;
  private final PluginSettingsFactory pluginSettingsFactory;
  private final TransactionTemplate transactionTemplate;

  public AdminConfigResource(UserManager userManager, PluginSettingsFactory pluginSettingsFactory,
                             TransactionTemplate transactionTemplate) {
    this.userManager = userManager;
    this.pluginSettingsFactory = pluginSettingsFactory;
    this.transactionTemplate = transactionTemplate;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response get(@Context HttpServletRequest request) {
    UserProfile user = userManager.getRemoteUser();
    if (user == null || !userManager.isSystemAdmin(user.getUserKey())) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    return Response.ok(transactionTemplate.execute(new TransactionCallback() {
      public Object doInTransaction() {
        PluginSettings settings = pluginSettingsFactory.createGlobalSettings();
        AdminConfig config = new AdminConfig();
        config.setEnabled(BooleanUtils.toBoolean((String) settings.get(AdminConfigResource.class.getName() + ".enabled")));
        config.setTtl(Integer.valueOf((String) settings.get(AdminConfigResource.class.getName() + ".ttl")));
        return config;
      }
    })).build();
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public Response put(final AdminConfig config, @Context HttpServletRequest request) {
    UserProfile user = userManager.getRemoteUser();
    if (user == null || !userManager.isSystemAdmin(user.getUserKey())) {
      return Response.status(Status.UNAUTHORIZED).build();
    }

    transactionTemplate.execute(new TransactionCallback() {
      public Object doInTransaction() {
        PluginSettings settings = pluginSettingsFactory.createGlobalSettings();
        settings.put(AdminConfigResource.class.getName() + ".enabled", BooleanUtils.toStringTrueFalse(config.getEnabled()));
        settings.put(AdminConfigResource.class.getName() + ".ttl", Integer.toString(config.getTtl()));
        return null;
      }
    });
    return Response.noContent().build();
  }


}