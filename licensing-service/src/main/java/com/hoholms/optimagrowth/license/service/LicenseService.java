package com.hoholms.optimagrowth.license.service;

import com.hoholms.optimagrowth.license.model.License;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Random;

@Service
public class LicenseService {

    MessageSource messages;

    public LicenseService(@Qualifier("messageSource") MessageSource messages) {
        this.messages = messages;
    }

    public License getLicense(String licenseId,
                              String organizationId) {
        return License.builder()
                .id(new Random().nextInt(1000))
                .licenseId(licenseId)
                .organizationId(organizationId)
                .description("Software product")
                .productName("O-stock")
                .licenseType("full")
                .build();
    }

    public String createLicense(License license, String organizationId, Locale locale) {
        String responseMessage = null;
        if (license != null) {
            license.setOrganizationId(organizationId);
            responseMessage = String.format(
                    messages.getMessage("license.create.message", null, locale),
                    license
            );
        }
        return responseMessage;
    }

    public String updateLicense(License license, String organizationId, Locale locale) {
        String responseMessage = null;
        if (license != null) {
            license.setOrganizationId(organizationId);
            responseMessage = String.format(
                    messages.getMessage("license.update.message", null, locale),
                    license
            );
        }
        return responseMessage;
    }

    public String deleteLicense(String licenseId, String organizationId, Locale locale) {
        return String.format(
                messages.getMessage("license.delete.message", null, locale),
                licenseId, organizationId);
    }
}
