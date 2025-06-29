package com.shubham.saas.user.service;

import com.shubham.saas.security.jwt.JwtProvider;
import com.shubham.saas.user.dto.AuthRequest;
import com.shubham.saas.user.dto.AuthResponse;
import com.shubham.saas.user.entity.User;
import com.shubham.saas.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public void register(AuthRequest request){
        log.info("Registering user...");
        boolean exists = userRepository.findByEmail(request.email()).isPresent();

        if (exists)
            throw new RuntimeException("Email already registered");

        String encodedPassword = passwordEncoder.encode(request.password());
        User user = new User(request.email(), encodedPassword, "USER");
        userRepository.save(user);
        log.info("User registered: {}", request.email());
    }

    public AuthResponse login(User user){
        log.info("Logging in user: {}", user.getEmail());
        String token = jwtProvider.generateToken(user);
        return new AuthResponse(token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
}
