package com.shubham.saas.tenant.dto;

public record TenantRegistrationRequest(
        String name,
        String adminEmail
) {
}
