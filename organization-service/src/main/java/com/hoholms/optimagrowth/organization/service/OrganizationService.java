package com.hoholms.optimagrowth.organization.service;

import com.hoholms.optimagrowth.organization.model.Organization;
import com.hoholms.optimagrowth.organization.repository.OrganizationRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationService {

  private OrganizationRepository repository;

  public Organization findById(String organizationId) {
    return repository
        .findById(organizationId)
        .orElseThrow(
            () ->
                new IllegalArgumentException("Can't find organization with id " + organizationId));
  }

  public Organization create(Organization organization) {
    organization.setOrganizationId(UUID.randomUUID().toString());
    return repository.save(organization);
  }

  public Organization update(Organization organization) {
    return repository.save(organization);
  }

  public void delete(Organization organization) {
    repository.delete(organization);
  }
}
