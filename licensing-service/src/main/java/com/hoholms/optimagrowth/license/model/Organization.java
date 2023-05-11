package com.hoholms.optimagrowth.license.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "organizations")
public class Organization {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "organization_id", nullable = false, length = -1)
    private String organizationId;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "contact_name")
    private String contactName;

    @Basic
    @Column(name = "contact_email")
    private String contactEmail;

    @Basic
    @Column(name = "contact_phone")
    private String contactPhone;
}
