package com.thundermoose.plugins;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.atlassian.stash.i18n.I18nService;
import com.atlassian.stash.user.UserService;
import org.apache.commons.codec.binary.Base64;

import com.thundermoose.plugins.admin.AdminConfigDao;
import com.thundermoose.plugins.admin.AdminConfig;
import com.thundermoose.plugins.user.UserConfigDao;
import com.thundermoose.plugins.user.UserConfig;
import com.thundermoose.plugins.paths.AdminPaths;
import com.thundermoose.plugins.paths.ProjectPaths;
import com.thundermoose.plugins.paths.RepoPaths;
import com.thundermoose.plugins.utils.KeyGenerator;
import com.thundermoose.plugins.utils.Encrypter;
import com.thundermoose.plugins.utils.Utils;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TokenAuthenticationHandlerTest{
  @Mock
  private UserService userService;
  @Mock
  private I18nService i18nService;
  @Mock
  private Utils utils;
  @Mock
  private UserConfigDao userDao;
  @Mock
  private AdminConfigDao adminDao;
  @InjectMocks
  private TokenAuthenticationHandler sut;

  AdminConfig adminConfig;

  @Before
  public void before(){
    MockitoAnnotations.initMocks(this);
    when(utils.generateTokenForUser(anyString(), anyInt())).thenReturn("asdfn2eonasdfjnasjdkf");
    adminConfig = new AdminConfig();
    adminConfig.setTtl(10);
    adminConfig.setEnabled(true);
    adminConfig.setKey(new KeyGenerator().generateKey());
    adminConfig.setAdminPaths(new AdminPaths(true, true, true, true));
    adminConfig.setProjectPaths(new ProjectPaths(true, true));
    adminConfig.setRepoPaths(new RepoPaths(true, true, true, true, true, true));
  }

  @Test
  public void testIsTokenValid_expired(){
    Encrypter encrypter = new Encrypter(Base64.decodeBase64(adminConfig.getKey()));
    String token = encrypter.encrypt("testuser:" + DateTime.now().plusDays(-50).getMillis() + ":" + DateTime.now().plusDays(10).getMillis() + ":" + UUID.randomUUID().toString());
    UserConfig userConfig = new UserConfig();
    userConfig.setToken(token);
    when(userDao.getUserConfig("testuser")).thenReturn(userConfig);
    when(adminDao.getAdminConfig()).thenReturn(adminConfig);

    assertFalse(sut.isTokenValid("/rest/api/1.0/projects/TEST/repos", "testuser", token));
  }

  @Test
  public void testIsTokenValid_unexpired(){
    Encrypter encrypter = new Encrypter(Base64.decodeBase64(adminConfig.getKey()));
    String token = encrypter.encrypt("testuser:" + DateTime.now().getMillis() + ":" + DateTime.now().plusDays(10).getMillis() + ":" + UUID.randomUUID().toString());
    UserConfig userConfig = new UserConfig();
    userConfig.setToken(token);
    when(userDao.getUserConfig("testuser")).thenReturn(userConfig);
    when(adminDao.getAdminConfig()).thenReturn(adminConfig);

    assertTrue(sut.isTokenValid("/rest/api/1.0/projects/TEST/repos", "testuser", token));
  }

  @Test
  public void testIsTokenValid_noexpiry(){
    adminConfig.setTtl(0);
    Encrypter encrypter = new Encrypter(Base64.decodeBase64(adminConfig.getKey()));
    String token = encrypter.encrypt("testuser:" + DateTime.now().plusDays(-100).getMillis() + ":" + DateTime.now().plusDays(-100).getMillis() + ":" + UUID.randomUUID().toString());
    UserConfig userConfig = new UserConfig();
    userConfig.setToken(token);
    when(userDao.getUserConfig("testuser")).thenReturn(userConfig);
    when(adminDao.getAdminConfig()).thenReturn(adminConfig);

    assertTrue(sut.isTokenValid("/rest/api/1.0/projects/TEST/repos", "testuser", token));
  }

  @Test
  public void testIsTokenValid_regenToken(){
    adminConfig.setTtl(0);
    Encrypter encrypter = new Encrypter(Base64.decodeBase64(adminConfig.getKey()));
    String token = encrypter.encrypt("testuser:" + DateTime.now().plusDays(-100).getMillis() + ":" + DateTime.now().plusDays(-100).getMillis() + ":" + UUID.randomUUID().toString());
    UserConfig userConfig = new UserConfig();
    userConfig.setToken(token);
    when(userDao.getUserConfig("testuser")).thenReturn(userConfig);
    when(adminDao.getAdminConfig()).thenReturn(adminConfig);

    String providedToken = encrypter.encrypt("testuser:" + DateTime.now().plusDays(-100).getMillis() + ":" + DateTime.now().plusDays(-100).getMillis() + ":" + UUID.randomUUID().toString());
    assertFalse(sut.isTokenValid("/rest/api/1.0/projects/TEST/repos", "testuser", providedToken));
  }
}
