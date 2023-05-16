package com.hoholms.optimagrowth.license.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.hoholms.optimagrowth.license.model.License;
import com.hoholms.optimagrowth.license.service.LicenseService;
import com.hoholms.optimagrowth.license.utils.UserContextHolder;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/organization/{organizationId}/license")
@AllArgsConstructor
@Slf4j
public class LicenseController {

  private LicenseService licenseService;

  @GetMapping(value = "/{licenseId}")
  public ResponseEntity<License> getLicense(
      @PathVariable("organizationId") String organizationId,
      @PathVariable("licenseId") String licenseId,
      @RequestHeader(value = "Accept-Language", required = false) Locale locale) {

    License license = licenseService.getLicense(organizationId, licenseId, locale);
    license.add(
        linkTo(
                methodOn(LicenseController.class)
                    .getLicense(organizationId, license.getLicenseId(), locale))
            .withSelfRel(),
        linkTo(methodOn(LicenseController.class).createLicense(license)).withRel("createLicense"),
        linkTo(methodOn(LicenseController.class).updateLicense(license)).withRel("updateLicense"),
        linkTo(
                methodOn(LicenseController.class)
                    .deleteLicense(license.getOrganizationId(), license.getLicenseId(), locale))
            .withRel("deleteLicense"));

    return ResponseEntity.ok(license);
  }

  @GetMapping
  public ResponseEntity<List<License>> getLicensesByOrganizationId(
      @PathVariable("organizationId") String organizationId)
      throws TimeoutException, ExecutionException, InterruptedException {
    log.debug(
        "LicenseServiceController Correlation id: {}",
        UserContextHolder.getContext().getCorrelationId());

    return ResponseEntity.ok(
        licenseService.getLicensesByOrganization(organizationId).get(5000, TimeUnit.MILLISECONDS));
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
      @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
    return ResponseEntity.ok(licenseService.deleteLicense(organizationId, licenseId, locale));
  }
}
