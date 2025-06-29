package com.shubham.saas.tenant.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HeaderTenantResolver implements TenantResolverStrategy{
    @Override
    public Optional<String> resolveTenant(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("X-Tenant-ID"));
    }
}