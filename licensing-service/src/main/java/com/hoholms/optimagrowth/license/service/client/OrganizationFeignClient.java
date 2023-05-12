package com.hoholms.optimagrowth.license.service.client;

import com.hoholms.optimagrowth.license.model.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("organization-service")
public interface OrganizationFeignClient {
  @GetMapping(value = "/v1/organization/{organizationId}", consumes = "application/json")
  Organization getOrganization(@PathVariable("organizationId") String organizationId);
}
