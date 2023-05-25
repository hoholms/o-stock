package com.hoholms.optimagrowth.license.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class UserContextHolder {

  private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();

  public static UserContext getContext() {
    UserContext context = userContext.get();

    if (context == null) {
      log.warn("UserContext is null");
      context = createEmptyContext();
      userContext.set(context);
    }

    log.info("Getting UserContext");
    return userContext.get();
  }

  public static void setContext(UserContext context) {
    Assert.notNull(context, "Only non-null UserContext instances are permitted");
    userContext.set(context);
    log.info("Set new UserContext");
  }

  public static void unload() {
    userContext.remove();
    log.info("Unload UserContext");
  }

  public static UserContext createEmptyContext() {
    log.info("Creating empty UserContext");
    return new UserContext();
  }
}
