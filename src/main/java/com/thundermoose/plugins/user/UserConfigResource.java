package com.thundermoose.plugins.user;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.sal.api.user.UserProfile;

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
  private final PluginSettingsFactory pluginSettingsFactory;
  private final TransactionTemplate transactionTemplate;

  public UserConfigResource(UserManager userManager, PluginSettingsFactory pluginSettingsFactory,
                            TransactionTemplate transactionTemplate) {
    this.userManager = userManager;
    this.pluginSettingsFactory = pluginSettingsFactory;
    this.transactionTemplate = transactionTemplate;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response get(@Context HttpServletRequest request) {
    final UserProfile user = userManager.getRemoteUser();
    final String username = user.getUsername();
    String token = getTokenForUser(user.getUsername());
    if (token == null) {
      token = transactionTemplate.execute(new TransactionCallback<String>() {
        public String doInTransaction() {
          PluginSettings settings = pluginSettingsFactory.createGlobalSettings();
          String token = generateTokenForUser(username);
          settings.put(UserConfigResource.class.getName() + "." + username + ".token", token);
          return token;
        }
      });
    }
    return Response.ok("\"" + token + "\"").build();
  }

  String getTokenForUser(final String username) {
    return transactionTemplate.execute(new TransactionCallback<String>() {
      public String doInTransaction() {
        PluginSettings settings = pluginSettingsFactory.createGlobalSettings();
        return (String) settings.get(UserConfigResource.class.getName() + "." + username + ".enabled");
      }
    });
  }

  String generateTokenForUser(String username) {
    return username + System.currentTimeMillis();
  }

}