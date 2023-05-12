package com.hoholms.optimagrowth.license.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "organizations")
public class Organization {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "organization_id", nullable = false)
  private String organizationId;

  @Basic
  @Column(name = "name", nullable = false)
  private String name;

  @Basic
  @Column(name = "contact_name", nullable = false)
  private String contactName;

  @Basic
  @Column(name = "contact_email", nullable = false)
  private String contactEmail;

  @Basic
  @Column(name = "contact_phone", nullable = false)
  private String contactPhone;
}
