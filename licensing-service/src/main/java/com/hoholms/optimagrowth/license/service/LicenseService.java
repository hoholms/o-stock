package com.hoholms.optimagrowth.license.service;

import com.hoholms.optimagrowth.license.config.ServiceConfig;
import com.hoholms.optimagrowth.license.model.License;
import com.hoholms.optimagrowth.license.model.Organization;
import com.hoholms.optimagrowth.license.repository.LicenseRepository;
import com.hoholms.optimagrowth.license.service.client.OrganizationFeignClient;
import java.util.Locale;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class LicenseService {

  private ServiceConfig config;
  private MessageSource messages;
  private LicenseRepository licenseRepository;

  private OrganizationFeignClient organizationFeignClient;

  public License getLicense(String organizationId, String licenseId, Locale locale) {
    License license =
        licenseRepository
            .findByOrganizationIdAndLicenseId(organizationId, licenseId)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        String.format(
                            messages.getMessage("license.search.error.message", null, locale),
                            licenseId,
                            organizationId)));

    Organization organization = organizationFeignClient.getOrganization(organizationId);

    if (null != organization) {
      license.setOrganizationName(organization.getName());
      license.setContactName(organization.getContactName());
      license.setContactEmail(organization.getContactEmail());
      license.setContactPhone(organization.getContactPhone());
    }

    return license.withComment(config.getProperty());
  }

  public License createLicense(License license) {
    license.setLicenseId(UUID.randomUUID().toString());
    licenseRepository.save(license);

    return license.withComment(config.getProperty());
  }

  public License updateLicense(License license) {
    licenseRepository.save(license);

    return license.withComment(config.getProperty());
  }

  @Transactional
  public String deleteLicense(String organizationId, String licenseId, Locale locale) {
    licenseRepository.deleteByOrganizationIdAndLicenseId(organizationId, licenseId);

    return String.format(
        messages.getMessage("license.delete.message", null, locale), licenseId, organizationId);
  }
}
