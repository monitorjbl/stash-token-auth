package com.thundermoose.plugins.paths;

public class RepoPaths implements Paths {
  @Matches({"/rest/api/1.0/projects/*/repos/*/permissions/**"})
  private boolean permissions;
  @Matches({
      "/rest/api/1.0/projects/*/repos/*/branches/**",
      "/rest/api/1.0/projects/*/repos/*/changes/**",
      "/rest/api/1.0/projects/*/repos/*/commits/**",
      "/rest/api/1.0/projects/*/repos/*/compare/**",
      "/rest/api/1.0/projects/*/repos/*/diff/**",
      "/rest/api/1.0/projects/*/repos/*/tags/**"
  })
  private boolean commitHistory;
  @Matches({
      "/rest/api/1.0/projects/*/repos/*/browse/**",
      "/rest/api/1.0/projects/*/repos/*/files/**"
  })
  private boolean files;
  @Matches({"/rest/api/1.0/projects/*/pull-requests/**"})
  private boolean pullReq;

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

  public boolean getPullReq() {
    return pullReq;
  }

  public void setPullReq(boolean pullReq) {
    this.pullReq = pullReq;
  }
}
