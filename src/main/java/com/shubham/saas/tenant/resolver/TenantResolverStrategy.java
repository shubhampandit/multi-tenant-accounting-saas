package com.shubham.saas.tenant.resolver;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface TenantResolverStrategy {
    Optional<String> resolveTenant(HttpServletRequest request);
}
