package com.thundermoose.plugins.paths;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SSHPaths implements Paths {
  @XmlElement
  @Matches({"/rest/ssh/1.0/keys/**"})
  private boolean userKeys;
  @XmlElement
  @Matches({
    "/rest/keys/1.0/ssh/**",
    "/rest/keys/1.0/projects/*/repos/**",
    "/rest/keys/1.0/projects/*/ssh",
    "/rest/keys/1.0/projects/*/ssh/**"
  })
  private boolean repoKeys;

  public SSHPaths(boolean userKeys, boolean repoKeys) {
    this.userKeys = userKeys;
    this.repoKeys = repoKeys;
  }

  public SSHPaths() {
  }

  public boolean getUserKeys() {
    return userKeys;
  }

  public void setUserKeys(boolean userKeys) {
    this.userKeys = userKeys;
  }

  public boolean getRepoKeys() {
    return repoKeys;
  }

  public void setRepoKeys(boolean repoKeys) {
    this.repoKeys = repoKeys;
  }
}
