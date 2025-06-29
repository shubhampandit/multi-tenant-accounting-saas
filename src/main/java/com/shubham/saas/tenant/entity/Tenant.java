package com.shubham.saas.tenant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tenants")
@NoArgsConstructor
@Data
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String schemaName;
    private String adminEmail;
    private LocalDateTime createdAt;

    public Tenant(String name, String schemaName, String adminEmail) {
        this.name = name;
        this.schemaName = schemaName;
        this.adminEmail = adminEmail;
        this.createdAt = LocalDateTime.now();
    }
}
