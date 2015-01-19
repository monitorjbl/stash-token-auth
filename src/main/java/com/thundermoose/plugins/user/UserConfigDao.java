package com.thundermoose.plugins.user;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.google.common.collect.Lists;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

public class UserConfigDao {
  public static final String BASE = UserConfig.class.getName();
  /**
   * PluginSettings does not have the ability to list properties, so we have to
   * track users by a List property
   */
  public static final String USER_LIST = BASE + ".users";
  private final PluginSettingsFactory pluginSettingsFactory;
  private final TransactionTemplate transactionTemplate;

  public UserConfigDao(PluginSettingsFactory pluginSettingsFactory, TransactionTemplate transactionTemplate) {
    this.pluginSettingsFactory = pluginSettingsFactory;
    this.transactionTemplate = transactionTemplate;
  }

  public UserConfig getUserConfig(final String username) {
    return readUserConfig(username, pluginSettingsFactory.createGlobalSettings());
  }

  public void setUserConfig(final String username, final UserConfig config) {
    transactionTemplate.execute(new TransactionCallback<UserConfig>() {
      public UserConfig doInTransaction() {
        PluginSettings settings = pluginSettingsFactory.createGlobalSettings();
        settings.put(BASE + "." + username + ".token", config.getToken());
        updateUserList(username);
        return config;
      }
    });
  }

  public List<UserConfig> getAllUserConfigs() {
    PluginSettings settings = pluginSettingsFactory.createGlobalSettings();
    List<UserConfig> configs = newArrayList();
    for (String username : getUserList()) {
      configs.add(readUserConfig(username, settings));
    }
    return configs;
  }

  private UserConfig readUserConfig(String username, PluginSettings settings) {
    UserConfig config = new UserConfig();
    config.setUsername(username);
    config.setToken((String) settings.get(BASE + "." + username + ".token"));
    return config;
  }

  /**
   * Updates the list of users to add a new one, if it does not already exist.
   *
   * @param username
   */
  @SuppressWarnings("unchecked")
  private void updateUserList(final String username) {
    transactionTemplate.execute(new TransactionCallback<Object>() {
      public Object doInTransaction() {
        PluginSettings settings = pluginSettingsFactory.createGlobalSettings();
        List<String> list = getUserList();
        list.add(username);
        list = newArrayList(newHashSet(list));
        settings.put(USER_LIST, list);
        return null;
      }
    });
  }

  /**
   * Returns the list of users with config data in the system
   *
   * @return
   */
  @SuppressWarnings("unchecked")
  private List<String> getUserList() {
    PluginSettings settings = pluginSettingsFactory.createGlobalSettings();
    List<String> list = (List<String>) settings.get(USER_LIST);
    return list == null ? Lists.<String>newArrayList() : list;
  }
}
