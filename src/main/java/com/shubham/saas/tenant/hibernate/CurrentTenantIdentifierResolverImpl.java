package com.shubham.saas.tenant.hibernate;

import com.shubham.saas.tenant.context.TenantContext;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    private static final String DEFAULT_TENANT = "public";

    @Override
    public @org.checkerframework.checker.nullness.qual.UnknownKeyFor
    @org.checkerframework.checker.nullness.qual.NonNull
    @org.checkerframework.checker.initialization.qual.Initialized String resolveCurrentTenantIdentifier() {
        String tenantId = TenantContext.getTenantId();
        return (tenantId != null) ? tenantId : DEFAULT_TENANT;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
