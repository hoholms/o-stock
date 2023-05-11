package com.hoholms.optimagrowth.license.service;

import com.hoholms.optimagrowth.license.config.ServiceConfig;
import com.hoholms.optimagrowth.license.model.License;
import com.hoholms.optimagrowth.license.repository.LicenseRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LicenseService {

    ServiceConfig config;
    private MessageSource messages;
    private LicenseRepository licenseRepository;

    public License getLicense(String organizationId,
                              String licenseId,
                              Locale locale) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(licenseId, organizationId)
                .orElseThrow(() -> new IllegalArgumentException(
                                String.format(
                                        messages.getMessage(
                                                "license.search.error.message",
                                                null, locale
                                        ),
                                        licenseId,
                                        organizationId
                                )
                        )
                );

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
    public String deleteLicense(String organizationId,
                                String licenseId,
                                Locale locale) {
        licenseRepository.deleteByOrganizationIdAndLicenseId(organizationId, licenseId);

        return String.format(
                messages.getMessage("license.delete.message", null, locale),
                licenseId,
                organizationId
        );
    }
}
