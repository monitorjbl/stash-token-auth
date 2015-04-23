package com.thundermoose.plugins.admin;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.thundermoose.plugins.paths.AdminPaths;
import com.thundermoose.plugins.paths.ProjectPaths;
import com.thundermoose.plugins.paths.RepoPaths;
import com.thundermoose.plugins.utils.KeyGenerator;
import org.apache.commons.lang3.BooleanUtils;

public class AdminConfigDao {
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
    if (config.getEnabled() == null) {
      config.setEnabled(true);
    }
    if (config.getKey() == null) {
      config.setKey(new KeyGenerator().generateKey());
    }
    if (config.getTtl() == null) {
      config.setTtl(30);
    }
    if (config.getAdminPaths() == null) {
      config.setAdminPaths(new AdminPaths(true, true, true, true));
    }
    if (config.getProjectPaths() == null) {
      config.setProjectPaths(new ProjectPaths(true, true));
    }
    if (config.getRepoPaths() == null) {
      config.setRepoPaths(new RepoPaths(true, true, true, true, true, true));
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

          if (settings.get(adminPathPrefix) != null) {
            config.setAdminPaths(new AdminPaths(
                BooleanUtils.toBoolean((String) settings.get(adminPermissions)),
                BooleanUtils.toBoolean((String) settings.get(adminUsers)),
                BooleanUtils.toBoolean((String) settings.get(adminGroups)),
                BooleanUtils.toBoolean((String) settings.get(adminLogs))
            ));
          }

          if (settings.get(projectPathPrefix) != null) {
            config.setProjectPaths(new ProjectPaths(
                BooleanUtils.toBoolean((String) settings.get(projectPermissions)),
                BooleanUtils.toBoolean((String) settings.get(projectRepoList))
            ));
          }

          if (settings.get(repoPathPrefix) != null) {
            config.setRepoPaths(new RepoPaths(
                BooleanUtils.toBoolean((String) settings.get(repoPermissions)),
                BooleanUtils.toBoolean((String) settings.get(repoCommitHistory)),
                BooleanUtils.toBoolean((String) settings.get(repoFiles)),
                BooleanUtils.toBoolean((String) settings.get(repoPullRequests)),
                BooleanUtils.toBoolean((String) settings.get(repoBranchPermissions)),
                BooleanUtils.toBoolean((String) settings.get(buildStatus))
            ));
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

        if (config.getAdminPaths() != null) {
          settings.put(adminPathPrefix, "true");
          settings.put(adminPermissions, BooleanUtils.toStringTrueFalse(config.getAdminPaths().getPermissions()));
          settings.put(adminUsers, BooleanUtils.toStringTrueFalse(config.getAdminPaths().getUsers()));
          settings.put(adminGroups, BooleanUtils.toStringTrueFalse(config.getAdminPaths().getGroups()));
          settings.put(adminLogs, BooleanUtils.toStringTrueFalse(config.getAdminPaths().getLogs()));
        }

        if (config.getProjectPaths() != null) {
          settings.put(projectPathPrefix, "true");
          settings.put(projectPermissions, BooleanUtils.toStringTrueFalse(config.getProjectPaths().getPermissions()));
          settings.put(projectRepoList, BooleanUtils.toStringTrueFalse(config.getProjectPaths().getRepoList()));
        }

        if (config.getRepoPaths() != null) {
          settings.put(repoPathPrefix, "true");
          settings.put(repoPermissions, BooleanUtils.toStringTrueFalse(config.getRepoPaths().getPermissions()));
          settings.put(repoCommitHistory, BooleanUtils.toStringTrueFalse(config.getRepoPaths().getCommitHistory()));
          settings.put(repoFiles, BooleanUtils.toStringTrueFalse(config.getRepoPaths().getFiles()));
          settings.put(repoPullRequests, BooleanUtils.toStringTrueFalse(config.getRepoPaths().getPullRequests()));
          settings.put(repoBranchPermissions, BooleanUtils.toStringTrueFalse(config.getRepoPaths().getBranchPermissions()));
          settings.put(buildStatus, BooleanUtils.toStringTrueFalse(config.getRepoPaths().getBuildStatus()));
        }

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

  public static final String BASE = AdminConfig.class.getName();
  public static final String adminPathPrefix = BASE + ".adminPaths";
  public static final String adminPermissions = adminPathPrefix + ".permissions";
  public static final String adminUsers = adminPathPrefix + ".users";
  public static final String adminGroups = adminPathPrefix + ".groups";
  public static final String adminLogs = adminPathPrefix + ".logs";
  public static final String projectPathPrefix = ".projectPaths";
  public static final String projectPermissions = projectPathPrefix + ".permissions";
  public static final String projectRepoList = projectPathPrefix + ".repoList";
  public static final String repoPathPrefix = BASE + ".repoPaths";
  public static final String repoPermissions = repoPathPrefix + ".permissions";
  public static final String repoCommitHistory = repoPathPrefix + ".commitHistory";
  public static final String repoFiles = repoPathPrefix + ".files";
  public static final String repoPullRequests = repoPathPrefix + ".pullRequests";
  public static final String repoBranchPermissions = repoPathPrefix + ".branchPermissions";
  public static final String buildStatus = repoPathPrefix + ".buildStatus";
}
