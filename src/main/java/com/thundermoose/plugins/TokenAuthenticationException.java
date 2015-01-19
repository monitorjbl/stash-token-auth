package com.thundermoose.plugins;

import com.atlassian.stash.i18n.KeyedMessage;
import com.atlassian.stash.user.AuthenticationException;

public class TokenAuthenticationException extends AuthenticationException {
  public TokenAuthenticationException(KeyedMessage message) {
    super(message);
  }
}
