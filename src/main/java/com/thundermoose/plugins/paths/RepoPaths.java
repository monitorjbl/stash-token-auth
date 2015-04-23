package com.thundermoose.plugins.paths;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RepoPaths implements Paths {
  @XmlElement
  @Matches({"/rest/api/1.0/projects/*/repos/*/permissions/**"})
  private boolean permissions;
  @XmlElement
  @Matches({
      "/rest/api/1.0/projects/*/repos/*/branches/**",
      "/rest/api/1.0/projects/*/repos/*/changes/**",
      "/rest/api/1.0/projects/*/repos/*/commits/**",
      "/rest/api/1.0/projects/*/repos/*/compare/**",
      "/rest/api/1.0/projects/*/repos/*/diff/**",
      "/rest/api/1.0/projects/*/repos/*/tags/**"
  })
  private boolean commitHistory;
  @XmlElement
  @Matches({
      "/rest/api/1.0/projects/*/repos/*/browse/**",
      "/rest/api/1.0/projects/*/repos/*/files/**"
  })
  private boolean files;
  @XmlElement
  @Matches({"/rest/api/1.0/projects/*/repos/*/pull-requests/**"})
  private boolean pullRequests;

  @XmlElement
  @Matches({
      "/rest/branch-permissions/1.0/projects/*/repos/*/permitted",
      "/rest/branch-permissions/1.0/projects/*/repos/*/restricted",
      "/rest/branch-permissions/1.0/projects/*/repos/*/restricted/*"
  })
  private boolean branchPermissions;

  @XmlElement
  @Matches({"/rest/build-status/1.0/commits/**"})
  private boolean buildStatus;

  public RepoPaths(boolean permissions, boolean commitHistory, boolean files, boolean pullRequests, boolean branchPermissions, boolean buildStatus) {
    this.permissions = permissions;
    this.commitHistory = commitHistory;
    this.files = files;
    this.pullRequests = pullRequests;
    this.branchPermissions = branchPermissions;
    this.buildStatus = buildStatus;
  }

  public RepoPaths() {
  }

  public boolean getPermissions() {
    return permissions;
  }

  public void setPermissions(boolean permissions) {
    this.permissions = permissions;
  }

  public boolean getCommitHistory() {
    return commitHistory;
  }

  public void setCommitHistory(boolean commitHistory) {
    this.commitHistory = commitHistory;
  }

  public boolean getFiles() {
    return files;
  }

  public void setFiles(boolean files) {
    this.files = files;
  }

  public boolean getPullRequests() {
    return pullRequests;
  }

  public void setPullRequests(boolean pullRequests) {
    this.pullRequests = pullRequests;
  }

  public boolean getBranchPermissions() {
    return branchPermissions;
  }

  public void setBranchPermissions(boolean branchPermissions) {
    this.branchPermissions = branchPermissions;
  }

  public boolean getBuildStatus() {
    return buildStatus;
  }

  public void setBuildStatus(boolean buildStatus) {
    this.buildStatus = buildStatus;
  }
}
