package com.thundermoose.plugins;

import com.atlassian.bitbucket.i18n.KeyedMessage;
import com.atlassian.bitbucket.auth.AuthenticationException;

public class TokenAuthenticationException extends AuthenticationException {
  public TokenAuthenticationException(KeyedMessage message) {
    super(message);
  }
}
