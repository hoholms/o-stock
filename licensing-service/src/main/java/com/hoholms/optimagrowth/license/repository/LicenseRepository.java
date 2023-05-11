package com.hoholms.optimagrowth.license.repository;

import com.hoholms.optimagrowth.license.model.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LicenseRepository extends JpaRepository<License, String> {

    Optional<License> findByOrganizationIdAndLicenseId(String organizationId, String licenseId);

    void deleteByOrganizationIdAndLicenseId(String organizationId, String licenseId);
}
