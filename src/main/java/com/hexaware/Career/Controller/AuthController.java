package com.hexaware.Career.Controller;

import com.hexaware.Career.Configuration.JwtUtil;
import com.hexaware.Career.Entity.User;
import com.hexaware.Career.Enum.Role;
import com.hexaware.Career.Repository.UserRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public static class RegisterRequest {
        public String email;
        public String password;
        public String role; // optional: "ROLE_JOBSEEKER", "ROLE_EMPLOYER", "ADMIN"
    }

    public static class LoginRequest {
        public String email;
        public String password;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) {

        if (userRepository.findByEmail(registerRequest.email).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already registered");
        }

        User user = new User();
        user.setEmail(registerRequest.email);
        user.setPassword(passwordEncoder.encode(registerRequest.password));

        // Convert string role to enum, fallback to ROLE_JOBSEEKER
        Role roleEnum;
        try {
            roleEnum = registerRequest.role != null ? Role.valueOf(registerRequest.role) : Role.ROLE_JOBSEEKER;
        } catch (IllegalArgumentException e) {
            roleEnum = Role.ROLE_JOBSEEKER;
        }
        user.setRole(roleEnum);

        userRepository.save(user);

        HttpHeaders header = new HttpHeaders();
        header.add("info", "User registered successfully");
        return new ResponseEntity<>("User registered successfully", header, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        Optional<User> userOptional = userRepository.findByEmail(loginRequest.email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(loginRequest.password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        // Generate JWT token with email and role name as string
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}
