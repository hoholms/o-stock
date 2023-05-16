package com.hoholms.optimagrowth.license.utils;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserContextFilter implements Filter {

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {

    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

    UserContext context =
        new UserContext(
            httpServletRequest.getHeader(UserContext.CORRELATION_ID),
            httpServletRequest.getHeader(UserContext.USER_ID),
            httpServletRequest.getHeader(UserContext.AUTH_TOKEN),
            httpServletRequest.getHeader(UserContext.ORGANIZATION_ID));

    UserContextHolder.setContext(context);

    log.debug(
        "UserContextFilter Correlation id: {}", UserContextHolder.getContext().getCorrelationId());

    filterChain.doFilter(httpServletRequest, servletResponse);
  }
}
