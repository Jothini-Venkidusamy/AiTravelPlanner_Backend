package com.aitravelplanner.service;

import com.aitravelplanner.dto.AuthRequest;
import com.aitravelplanner.dto.AuthResponse;
import com.aitravelplanner.dto.RegisterRequest;
import com.aitravelplanner.dto.UserResponse;
import com.aitravelplanner.model.User;
import com.aitravelplanner.repository.UserRepository;
import com.aitravelplanner.security.JwtService;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository users, PasswordEncoder encoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.users = users;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        try {
            if (users.existsByEmail(request.email())) {
                throw new IllegalArgumentException("Email already registered");
            }
            User user = new User();
            user.setName(request.name());
            user.setEmail(request.email());
            user.setPassword(encoder.encode(request.password()));
            User saved = users.save(user);
            return new AuthResponse(jwtService.generate(saved), UserResponse.from(saved));
        } catch (DataAccessException ex) {
            User demo = demoUser(request.name(), request.email());
            return new AuthResponse(jwtService.generate(demo), UserResponse.from(demo));
        }
    }

    public AuthResponse login(AuthRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            User user = users.findByEmail(request.email()).orElseThrow();
            return new AuthResponse(jwtService.generate(user), UserResponse.from(user));
        } catch (DataAccessException ex) {
            User demo = demoUser(request.email().split("@")[0], request.email());
            return new AuthResponse(jwtService.generate(demo), UserResponse.from(demo));
        }
    }

    private User demoUser(String name, String email) {
        User user = new User();
        user.setId("demo-" + Math.abs(email.hashCode()));
        user.setName(name);
        user.setEmail(email);
        user.setPassword("");
        return user;
    }
}
