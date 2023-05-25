package com.hoholms.optimagrowth.license.utils;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserContextInterceptor implements RequestInterceptor {

  @Override
  public void apply(RequestTemplate template) {
    template.header(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
    template.header(UserContext.AUTH_TOKEN, UserContextHolder.getContext().getAuthToken());
  }
}
