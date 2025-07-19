package com.hexaware.Career.Configuration;

import com.hexaware.Career.Entity.User;
import com.hexaware.Career.Repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {

        // ➡️ 1. Skip JWT logic for public endpoints
        String path = request.getRequestURI();
        if (path.startsWith("/user/register") || path.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        // ➡️ 2. Extract token from Authorization header (if present)
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                email = jwtUtil.extractEmail(token);
            } catch (Exception e) {
                // Invalid token → ignore; continue without authentication
            }
        }

        // ➡️ 3. Validate token & set authentication in context
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent() && jwtUtil.validateToken(token, email)) {
                User user = userOptional.get();
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // ➡️ 4. Continue filter chain
        filterChain.doFilter(request, response);
    }
}
