package com.thundermoose.plugins.paths;

public class ProjectPaths implements Paths {
  @Matches({"/rest/api/1.0/projects/*/permissions/**"})
  private boolean permissions;
  @Matches({"/rest/api/1.0/projects/*/repos/**"})
  private boolean repoList;

  public boolean getPermissions() {
    return permissions;
  }

  public void setPermissions(boolean permissions) {
    this.permissions = permissions;
  }

  public boolean getRepoList() {
    return repoList;
  }

  public void setRepoList(boolean repoList) {
    this.repoList = repoList;
  }
}
