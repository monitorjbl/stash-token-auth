package com.thundermoose.plugins.paths;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SSHPaths implements Paths {
  @XmlElement
  @Matches({"/rest/keys/1.0/ssh/**"})
  private boolean userkeys;
  @XmlElement
  @Matches({"/rest/ssh/1.0/keys/**"})
  private boolean repokeys;

  public SSHPaths(boolean userkeys, boolean repokeys) {
    this.userkeys = userkeys;
    this.repokeys = repokeys;
  }

  public SSHPaths() {
  }

  public boolean getUserKeys() {
    return userkeys;
  }

  public void setUserKeys(boolean userkeys) {
    this.userkeys = userkeys;
  }

  public boolean getRepoKeys() {
    return repokeys;
  }

  public void setRepoKeys(boolean repokeys) {
    this.repokeys = repokeys;
  }
}
