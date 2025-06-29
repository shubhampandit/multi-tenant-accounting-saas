package com.shubham.saas.user.controller;

import com.shubham.saas.user.dto.AuthRequest;
import com.shubham.saas.user.dto.AuthResponse;
import com.shubham.saas.user.entity.User;
import com.shubham.saas.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody AuthRequest request){
        authService.register(request);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request){
        UsernamePasswordAuthenticationToken userDetails = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var authUser = authenticationManager.authenticate(userDetails);
        return ResponseEntity.ok(authService.login((User) authUser.getPrincipal()));
    }
}
