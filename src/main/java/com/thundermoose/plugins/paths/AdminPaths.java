package com.thundermoose.plugins.paths;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AdminPaths implements Paths {
  @XmlElement
  @Matches({"/rest/api/1.0/admin/permissions/**"})
  private boolean permissions;
  @XmlElement
  @Matches({"/rest/api/1.0/admin/users/**"})
  private boolean users;
  @XmlElement
  @Matches({"/rest/api/1.0/admin/groups/**"})
  private boolean groups;
  @XmlElement
  @Matches({"/rest/api/1.0/admin/logs/**"})
  private boolean logs;

  public AdminPaths(boolean permissions, boolean users, boolean groups, boolean logs) {
    this.permissions = permissions;
    this.users = users;
    this.groups = groups;
    this.logs = logs;
  }

  public AdminPaths() {
  }

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
