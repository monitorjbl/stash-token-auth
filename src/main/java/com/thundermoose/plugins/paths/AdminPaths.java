package com.thundermoose.plugins.paths;

public class AdminPaths implements Paths {
  @Matches({"/rest/api/1.0/admin/permissions/**"})
  private boolean permissions;
  @Matches({"/rest/api/1.0/admin/users/**"})
  private boolean users;
  @Matches({"/rest/api/1.0/admin/groups/**"})
  private boolean groups;
  @Matches({"/rest/api/1.0/admin/logs/**"})
  private boolean logs;

  public boolean getPermissions() {
    return permissions;
  }

  public void setPermissions(boolean permissions) {
    this.permissions = permissions;
  }

  public boolean getUsers() {
    return users;
  }

  public void setUsers(boolean users) {
    this.users = users;
  }

  public boolean getGroups() {
    return groups;
  }

  public void setGroups(boolean groups) {
    this.groups = groups;
  }

  public boolean getLogs() {
    return logs;
  }

  public void setLogs(boolean logs) {
    this.logs = logs;
  }
}
