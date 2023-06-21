package com.petstore.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final CustomUserDetailsService userDetailsService;

    private String jwt;
    private String username;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            checkIfAuthenticated(authHeader);
            checkIfAuthorized(authHeader);
        }
        filterChain.doFilter(request, response);
    }

    private void checkIfAuthenticated(String authHeader) {
        if (authHeader.startsWith("Basic ")) {
            jwt = authHeader.substring(6);
            username = jwtService.getCredentials(jwt)[0];
            String password = jwtService.getCredentials(jwt)[1];
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

    private void checkIfAuthorized(String authHeader) {
        if (authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = jwtService.extractUsername(jwt);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
    }
}