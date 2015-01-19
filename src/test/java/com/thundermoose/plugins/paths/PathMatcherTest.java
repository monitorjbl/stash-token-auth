package com.thundermoose.plugins.paths;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PathMatcherTest {

  @Test
  public void testAllowedSimple() {
    AdminPaths adminPaths = new AdminPaths();
    adminPaths.setGroups(true);
    PathMatcher sut = new PathMatcher(adminPaths);

    assertTrue(sut.pathAllowed("/rest/api/1.0/admin/groups"));
    assertTrue(sut.pathAllowed("/rest/api/1.0/admin/groups/add-user"));
  }

  @Test
  public void testDisallowedSimple() {
    AdminPaths adminPaths = new AdminPaths();
    adminPaths.setGroups(false);
    PathMatcher sut = new PathMatcher(adminPaths);

    assertFalse(sut.pathAllowed("/rest/api/1.0/admin/groups"));
    assertFalse(sut.pathAllowed("/rest/api/1.0/admin/groups/add-user"));
  }

  @Test
  public void testAllowedComplex() {
    RepoPaths repoPaths = new RepoPaths();
    repoPaths.setFiles(true);
    PathMatcher sut = new PathMatcher(repoPaths);

    assertTrue(sut.pathAllowed("/rest/api/1.0/projects/ASDF/repos/test-repo/files"));
    assertTrue(sut.pathAllowed("/rest/api/1.0/projects/ASDF/repos/test-repo/files/README.md"));
    assertTrue(sut.pathAllowed("/rest/api/1.0/projects/ASDF/repos/test-repo/browse"));
    assertTrue(sut.pathAllowed("/rest/api/1.0/projects/ASDF/repos/test-repo/browse/src"));
  }

  @Test
  public void testDisallowedComplex() {
    RepoPaths repoPaths = new RepoPaths();
    repoPaths.setFiles(false);
    PathMatcher sut = new PathMatcher(repoPaths);

    assertFalse(sut.pathAllowed("/rest/api/1.0/projects/ASDF/repos/test-repo/files"));
    assertFalse(sut.pathAllowed("/rest/api/1.0/projects/ASDF/repos/test-repo/files/README.md"));
    assertFalse(sut.pathAllowed("/rest/api/1.0/projects/ASDF/repos/test-repo/browse"));
    assertFalse(sut.pathAllowed("/rest/api/1.0/projects/ASDF/repos/test-repo/browse/src"));
  }

  @Test
  public void testMultiplePaths() {
    RepoPaths repoPaths = new RepoPaths();
    repoPaths.setFiles(true);
    AdminPaths adminPaths = new AdminPaths();
    adminPaths.setGroups(true);
    PathMatcher sut = new PathMatcher(repoPaths, adminPaths);

    assertTrue(sut.pathAllowed("/rest/api/1.0/projects/ASDF/repos/test-repo/files/README.md"));
    assertTrue(sut.pathAllowed("/rest/api/1.0/admin/groups/add-user"));
  }

}
