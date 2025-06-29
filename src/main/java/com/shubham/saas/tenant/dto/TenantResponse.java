package com.shubham.saas.tenant.dto;

public record TenantResponse(
        Long tenantId,
        String schema,
        String status
) {
}
