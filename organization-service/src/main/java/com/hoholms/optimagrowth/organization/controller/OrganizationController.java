package com.hoholms.optimagrowth.organization.controller;

import com.hoholms.optimagrowth.organization.model.Organization;
import com.hoholms.optimagrowth.organization.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/organization")
@AllArgsConstructor
public class OrganizationController {

  private OrganizationService organizationService;

  @GetMapping("/{organizationId}")
  public ResponseEntity<Organization> getOrganization(@PathVariable String organizationId) {
    return ResponseEntity.ok(organizationService.findById(organizationId));
  }

  @PutMapping("/{organizationId}")
  public ResponseEntity<Organization> updateOrganization(
      @PathVariable String organizationId, @RequestBody Organization organization) {
    return ResponseEntity.ok(organizationService.update(organization));
  }

  @PostMapping
  public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) {
    return ResponseEntity.ok(organizationService.create(organization));
  }

  @DeleteMapping("/{organizationId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteOrganization(
      @PathVariable String organizationId, @RequestBody Organization organization) {
    organizationService.delete(organization);
  }
}
