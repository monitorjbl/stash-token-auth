package com.thundermoose.plugins.user;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;

public class UserConfigDao {
  public static final String BASE = UserConfig.class.getName();
  private final PluginSettingsFactory pluginSettingsFactory;
  private final TransactionTemplate transactionTemplate;

  public UserConfigDao(PluginSettingsFactory pluginSettingsFactory, TransactionTemplate transactionTemplate) {
    this.pluginSettingsFactory = pluginSettingsFactory;
    this.transactionTemplate = transactionTemplate;
  }

  public UserConfig getUserConfig(final String username) {
    return transactionTemplate.execute(new TransactionCallback<UserConfig>() {
      public UserConfig doInTransaction() {
        PluginSettings settings = pluginSettingsFactory.createGlobalSettings();
        UserConfig config = new UserConfig();
        config.setToken((String) settings.get(BASE + "." + username + ".token"));
        return config;
      }
    });
  }

  public void setUserConfig(final String username, final UserConfig config) {
    transactionTemplate.execute(new TransactionCallback<UserConfig>() {
      public UserConfig doInTransaction() {
        PluginSettings settings = pluginSettingsFactory.createGlobalSettings();
        settings.put(BASE + "." + username + ".token", config.getToken());
        return config;
      }
    });
  }
}
