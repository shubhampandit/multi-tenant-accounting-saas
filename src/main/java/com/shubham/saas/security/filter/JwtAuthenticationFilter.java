package com.shubham.saas.security.filter;

import com.shubham.saas.security.jwt.JwtProvider;
import com.shubham.saas.tenant.context.TenantContext;
import com.shubham.saas.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if(null == header || !header.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = header.substring(7);
            Claims claims = jwtProvider.extractClaims(token);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (jwtProvider.isValidToken(claims) && null == authentication){
                String email = claims.getSubject();
                String tenant = (String) claims.get("tenant");

                if(null != tenant){
                    TenantContext.setTenantId(tenant);
                }

                var user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Invalid Credential"));
                var auth = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e){
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }
}
