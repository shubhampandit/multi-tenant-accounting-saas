package com.shubham.saas.tenant.controller;

import com.shubham.saas.tenant.dto.TenantRegistrationRequest;
import com.shubham.saas.tenant.dto.TenantResponse;
import com.shubham.saas.tenant.entity.Tenant;
import com.shubham.saas.tenant.service.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {
    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerTenant(@RequestBody TenantRegistrationRequest request){
        Tenant tenant = tenantService.registerTenant(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new TenantResponse(tenant.getId(), tenant.getSchemaName(), "CREATED"));
    }
}
