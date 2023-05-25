package com.hoholms.optimagrowth.license.service;

import com.hoholms.optimagrowth.license.config.ServiceConfig;
import com.hoholms.optimagrowth.license.model.License;
import com.hoholms.optimagrowth.license.model.Organization;
import com.hoholms.optimagrowth.license.repository.LicenseRepository;
import com.hoholms.optimagrowth.license.utils.UserContextHolder;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class LicenseService {

  private ServiceConfig config;
  private MessageSource messages;
  private LicenseRepository licenseRepository;
  private OrganizationService organizationService;

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

    Organization organization = organizationService.getOrganization(organizationId);

    if (null != organization) {
      license.setOrganizationName(organization.getName());
      license.setContactName(organization.getContactName());
      license.setContactEmail(organization.getContactEmail());
      license.setContactPhone(organization.getContactPhone());
    }

    return license.withComment(config.getProperty());
  }

  @CircuitBreaker(name = "licenseService", fallbackMethod = "buildFallbackLicenseList")
  @RateLimiter(name = "licenseService", fallbackMethod = "buildFallbackLicenseList")
  @Retry(name = "retryLicenseService", fallbackMethod = "buildFallbackLicenseList")
  @TimeLimiter(name = "timelimiterLicenseService", fallbackMethod = "buildFallbackLicenseList")
  @Bulkhead(
      name = "bulkheadLicenseService",
      type = Bulkhead.Type.THREADPOOL,
      fallbackMethod = "buildFallbackLicenseList")
  public CompletableFuture<List<License>> getLicensesByOrganization(String organizationId)
      throws TimeoutException {
    log.debug(
        "getLicensesByOrganization Correlation id: {}",
        UserContextHolder.getContext().getCorrelationId());

    randomlyRunLong();
    return CompletableFuture.supplyAsync(
        () -> licenseRepository.findByOrganizationId(organizationId));
  }

  @SuppressWarnings("unused")
  private CompletableFuture<List<License>> buildFallbackLicenseList(
      String organizationId, Throwable t) {
    log.error(t.getMessage());

    License license =
        License.builder()
            .licenseId("0000000-00-00000")
            .organizationId(organizationId)
            .productName("Sorry, no licensing information currently available")
            .build();

    return CompletableFuture.supplyAsync(() -> List.of(license));
  }

  private void randomlyRunLong() throws TimeoutException {
    Random rand = new Random();
    int randomNum = rand.nextInt((3 - 1) + 1) + 1;
    if (randomNum == 3) {
      sleep();
    }
  }

  private void sleep() throws TimeoutException {
    try {
      log.info("Sleep");
      Thread.sleep(5000);
      throw new TimeoutException("Timeout reached");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error(e.getMessage());
    }
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
