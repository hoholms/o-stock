package com.hoholms.optimagrowth.license.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.hoholms.optimagrowth.license.model.License;
import com.hoholms.optimagrowth.license.service.LicenseService;
import java.util.Locale;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/organization/{organizationId}/license")
@AllArgsConstructor
public class LicenseController {

    private LicenseService licenseService;

    @GetMapping(value = "/{licenseId}")
    public ResponseEntity<License> getLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId,
            @RequestHeader(value = "Accept-Language", required = false)
            Locale locale
    ) {

        License license = licenseService.getLicense(organizationId, licenseId, locale);
        license.add(
                linkTo(methodOn(LicenseController.class)
                        .getLicense(organizationId, license.getLicenseId(), locale))
                        .withSelfRel(),
                linkTo(methodOn(LicenseController.class)
                        .createLicense(license))
                        .withRel("createLicense"),
                linkTo(methodOn(LicenseController.class)
                        .updateLicense(license))
                        .withRel("updateLicense"),
                linkTo(methodOn(LicenseController.class)
                        .deleteLicense(license.getOrganizationId(), license.getLicenseId(), locale))
                        .withRel("deleteLicense")
        );

        return ResponseEntity.ok(license);
    }

    @PutMapping
    public ResponseEntity<License> updateLicense(@RequestBody License request) {
        return ResponseEntity.ok(licenseService.updateLicense(request));
    }

    @PostMapping
    public ResponseEntity<License> createLicense(@RequestBody License request) {
        return ResponseEntity.ok(licenseService.createLicense(request));
    }

    @DeleteMapping(value = "/{licenseId}", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> deleteLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId,
            @RequestHeader(value = "Accept-Language", required = false)
            Locale locale
    ) {
        return ResponseEntity.ok(licenseService.deleteLicense(organizationId, licenseId, locale));
    }
}
