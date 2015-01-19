package com.thundermoose.plugins.admin;

import com.thundermoose.plugins.paths.AdminPaths;
import com.thundermoose.plugins.paths.ProjectPaths;
import com.thundermoose.plugins.paths.RepoPaths;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AdminConfig {
  @XmlElement
  private Boolean enabled;
  @XmlElement
  private Integer ttl;
  @XmlElement
  private String key;

  @XmlElement
  private AdminPaths adminPaths;
  @XmlElement
  private ProjectPaths projectPaths;
  @XmlElement
  private RepoPaths repoPaths;

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Integer getTtl() {
    return ttl;
  }

  public void setTtl(Integer ttl) {
    this.ttl = ttl;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public AdminPaths getAdminPaths() {
    return adminPaths;
  }

  public void setAdminPaths(AdminPaths adminPaths) {
    this.adminPaths = adminPaths;
  }

  public ProjectPaths getProjectPaths() {
    return projectPaths;
  }

  public void setProjectPaths(ProjectPaths projectPaths) {
    this.projectPaths = projectPaths;
  }

  public RepoPaths getRepoPaths() {
    return repoPaths;
  }

  public void setRepoPaths(RepoPaths repoPaths) {
    this.repoPaths = repoPaths;
  }
}
