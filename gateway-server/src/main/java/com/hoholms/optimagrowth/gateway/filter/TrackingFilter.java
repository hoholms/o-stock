package com.hoholms.optimagrowth.gateway.filter;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(1)
@Component
@Slf4j
@AllArgsConstructor
public class TrackingFilter implements GlobalFilter {

  FilterUtils filterUtils;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    HttpHeaders requestHeaders = exchange.getRequest().getHeaders();

    if (isCorrelationIdPresent(requestHeaders)) {
      log.debug(
          "tmx-correlation-id found in tracking filter: {}. ",
          filterUtils.getCorrelationId(requestHeaders));
    } else {
      String correlationId = generateCorrelationId();
      exchange = filterUtils.setCorrelationId(exchange, correlationId);

      log.debug("tmx-correlation-id generated in tracking filter: {}.", correlationId);
    }

    return chain.filter(exchange);
  }

  private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
    return filterUtils.getCorrelationId(requestHeaders) != null;
  }

  private String generateCorrelationId() {
    return UUID.randomUUID().toString();
  }
}
