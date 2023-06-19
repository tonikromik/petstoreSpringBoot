//package com.petstore.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class CustomAuthManager implements AuthenticationManager {
//    private final CustomUserDetailsService customUserDetailsService;
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        UsernamePasswordAuthenticationToken authToken = null;
//
//        if (authentication == null) {
//            return null;
//        }
//        String name = String.valueOf(authentication.getName());
//        String pass = String.valueOf(authentication.getCredentials());
//
//        var user = customUserDetailsService.loadUserByUsername(name);
//
//        if (user.getPassword().equals(pass)) {
//            authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
//        }
//        return authToken;
//    }
//}
