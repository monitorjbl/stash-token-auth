package com.thundermoose.plugins.paths;

import com.thundermoose.plugins.utils.Utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class PathMatcher {
  private final Map<String, PathInfo> paths = new HashMap<>();

  public PathMatcher(Paths... paths) {
    for (Paths p : paths) {
      init(p);
    }
  }

  private void init(Paths p) {
    Class cls = p.getClass();
    try {
      for (Field f : cls.getDeclaredFields()) {
        Matches m = f.getAnnotation(Matches.class);
        if (m != null) {
          f.setAccessible(true);
          boolean allowed = f.getBoolean(p);
          for (String str : m.value()) {
            paths.put(Utils.createRegexFromGlob(str), new PathInfo(allowed,
                Pattern.compile(Utils.createRegexFromGlob(str))));
          }
        }
      }
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean pathAllowed(String path) {
    for (String key : paths.keySet()) {
      PathInfo pi = paths.get(key);
      if (pi.matcher.matcher(path).matches() && pi.enabled) {
        return true;
      }
    }
    return false;
  }

  private static class PathInfo {
    boolean enabled;
    Pattern matcher;

    public PathInfo(boolean enabled, Pattern matcher) {
      this.enabled = enabled;
      this.matcher = matcher;
    }
  }
}
