package com.shubham.saas.tenant.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TenantResolverChain {
    private final List<TenantResolverStrategy> strategies;

    public TenantResolverChain(List<TenantResolverStrategy> strategies) {
        this.strategies = strategies;
    }

    public Optional<String> resolve(HttpServletRequest request){
        return strategies.stream()
                .map(s -> s.resolveTenant(request))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }
}