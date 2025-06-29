package com.shubham.saas.tenant.service;

import com.shubham.saas.tenant.context.TenantContext;
import com.shubham.saas.tenant.dto.TenantRegistrationRequest;
import com.shubham.saas.tenant.entity.Tenant;
import com.shubham.saas.tenant.repository.TenantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TenantService {
    private final TenantRepository tenantRepository;
    private final SchemaService schemaService;
    private final TenantUserService tenantUserService;

    public TenantService(TenantRepository tenantRepository, SchemaService schemaService, TenantUserService tenantUserService) {
        this.tenantRepository = tenantRepository;
        this.schemaService = schemaService;
        this.tenantUserService = tenantUserService;
    }

    @Transactional
    public Tenant registerTenant(TenantRegistrationRequest request){
        String schemaName = generateSchemaName(request.name());

        if (tenantRepository.existsBySchemaName(schemaName))
            throw new IllegalArgumentException("Tenant already exists.");

        // Step 1: Create Tenant Entry
        Tenant tenant = new Tenant(request.name(), schemaName, request.adminEmail());

        // Step 2: Initialize Schema + Migrate
        schemaService.initTenantSchema(schemaName);

        // Step 3: Create admin user
        tenantUserService.createAdminUser(schemaName, tenant.getAdminEmail(), "123456789");

        return tenantRepository.save(tenant);
    }

    private String generateSchemaName(String name) {
        return "tenant_" + name.toLowerCase().replaceAll("[^a-z0-9]","");
    }
}
