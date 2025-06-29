package com.shubham.saas.security.jwt;

import com.shubham.saas.tenant.context.TenantContext;
import com.shubham.saas.tenant.repository.TenantRepository;
import com.shubham.saas.user.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {
    private final Long EXPIRATION = 1000*60*60L;
    private static final String SECRET_KEY = "my-secret-key-hello-world-are-you-there-all-is-well";

    public String generateToken(User user){
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole())
                .claim("tenant", TenantContext.getTenantId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .compact();
    }

    public Claims extractClaims(String token){
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("JWT token is expired", e);
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("JWT token is unsupported", e);
        } catch (MalformedJwtException e) {
            throw new RuntimeException("JWT token is malformed", e);
        } catch (SecurityException e) {
            throw new RuntimeException("JWT signature validation failed", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("JWT token compact of handler are invalid", e);
        }
    }

    public boolean isValidToken(Claims claims){
        return null != claims.getSubject()
                && new Date().before(claims.getExpiration());
    }

    private SecretKey getSecretKey(){
        byte[] keyBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
