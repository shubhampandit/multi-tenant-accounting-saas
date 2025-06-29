package com.shubham.saas.tenant.filter;

import com.shubham.saas.tenant.context.TenantContext;
import com.shubham.saas.tenant.resolver.TenantResolverChain;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TenantResolverFilter extends OncePerRequestFilter {

    private final TenantResolverChain tenantResolverChain;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Optional<String> tenant = tenantResolverChain.resolve(request);
        tenant.ifPresent(TenantContext::setTenantId);
        filterChain.doFilter(request,response);
    }
}
