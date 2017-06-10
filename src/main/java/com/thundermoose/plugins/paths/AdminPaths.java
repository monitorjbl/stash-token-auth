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
  @XmlElement
  @Matches({"/rest/api/1.0/**"})
  private boolean allRestApi;
  @XmlElement
  @Matches({"/rest/branch-utils/1.0/**"})
  private boolean allBranchUtilsApi;
  @XmlElement
  @Matches({"/rest/keys/1.0/**"})
  private boolean allKeysApi;
  @XmlElement
  @Matches({"/rest/default-reviewers/1.0/**"})
  private boolean allDefaultReviewersApi;

  public AdminPaths(boolean permissions, boolean users, boolean groups, boolean logs, boolean allRestApi, boolean allBranchUtilsApi, boolean  allKeysApi, boolean allDefaultReviewersApi) {
    this.permissions = permissions;
    this.users = users;
    this.groups = groups;
    this.logs = logs;
    this.allRestApi = allRestApi;
    this.allBranchUtilsApi = allBranchUtilsApi;
    this.allKeysApi = allKeysApi;
    this.allDefaultReviewersApi = allDefaultReviewersApi;
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

  public boolean getAllRestApi() {
    return allRestApi;
  }

  public void setAllRestApi(boolean allRestApi) {
    this.allRestApi = allRestApi;
  }

  public boolean getAllBranchUtilsApi() {
    return allBranchUtilsApi;
  }

  public void setAllBranchUtilsApi(boolean allBranchUtilsApi) {
    this.allBranchUtilsApi = allBranchUtilsApi;
  }

  public boolean getAllKeysApi() {
    return allKeysApi;
  }

  public void setAllKeysApi(boolean allKeysApi) {
    this.allKeysApi = allKeysApi;
  }

  public boolean getAllDefaultReviewersApi() {
    return allDefaultReviewersApi;
  }

  public void setAllDefaultReviewersApi(boolean allDefaultReviewersApi) {
    this.allDefaultReviewersApi = allDefaultReviewersApi;
  }
}
