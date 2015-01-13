package com.thundermoose.plugins.admin;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.thundermoose.plugins.utils.KeyGenerator;
import org.apache.commons.lang3.BooleanUtils;

public class AdminConfigDao {
  public static final String BASE = AdminConfig.class.getName();
  private final PluginSettingsFactory pluginSettingsFactory;
  private final TransactionTemplate transactionTemplate;
  private AdminConfig cachedConfig;

  public AdminConfigDao(PluginSettingsFactory pluginSettingsFactory, TransactionTemplate transactionTemplate) {
    this.pluginSettingsFactory = pluginSettingsFactory;
    this.transactionTemplate = transactionTemplate;
    setDefaults();
  }

  private void setDefaults() {
    AdminConfig config = getAdminConfig();
    if (config.getEnabled()) {
      config.setEnabled(true);
    }
    if (config.getKey() == null) {
      config.setKey(new KeyGenerator().generateKey());
    }
    if (config.getTtl() == null) {
      config.setTtl(30);
    }
    setAdminConfig(config);
  }

  public AdminConfig getAdminConfig() {
    AdminConfig config = getCache();
    if (config != null) {
      return config;
    } else {
      return transactionTemplate.execute(new TransactionCallback<AdminConfig>() {
        public AdminConfig doInTransaction() {
          PluginSettings settings = pluginSettingsFactory.createGlobalSettings();
          AdminConfig config = new AdminConfig();
          config.setEnabled(BooleanUtils.toBoolean((String) settings.get(BASE + ".enabled")));
          config.setKey((String) settings.get(BASE + ".key"));
          String ttl = (String) settings.get(BASE + ".ttl");
          if (ttl != null) {
            config.setTtl(Integer.valueOf(ttl));
          }
          return config;
        }
      });
    }
  }

  public void setAdminConfig(final AdminConfig config) {
    transactionTemplate.execute(new TransactionCallback<AdminConfig>() {
      public AdminConfig doInTransaction() {
        PluginSettings settings = pluginSettingsFactory.createGlobalSettings();
        settings.put(BASE + ".enabled", BooleanUtils.toStringTrueFalse(config.getEnabled()));
        settings.put(BASE + ".ttl", Integer.toString(config.getTtl()));
        settings.put(BASE + ".key", config.getKey());
        setCache(config);
        return config;
      }
    });
  }

  private synchronized void setCache(AdminConfig config) {
    this.cachedConfig = config;
  }

  private synchronized AdminConfig getCache() {
    return this.cachedConfig;
  }

}
