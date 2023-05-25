package com.hoholms.optimagrowth.license.service;

import com.hoholms.optimagrowth.license.model.Organization;
import com.hoholms.optimagrowth.license.service.client.OrganizationFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class OrganizationService {

  private OrganizationFeignClient organizationFeignClient;

  @CircuitBreaker(name = "organizationService")
  public Organization getOrganization(String organizationId) {
    return organizationFeignClient.getOrganization(organizationId);
  }
}
