//package com.petstore.petstoreRest.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//import java.util.function.Function;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
////@Configuration
//public class SecurityConfig {
//
//// in memory object
////    @Bean
//    public InMemoryUserDetailsManager createUserDetailsManager() {
//        UserDetails userDetails = createNewUser("admin", "admin");
//        return new InMemoryUserDetailsManager(userDetails);
//    }
//
//    private UserDetails createNewUser(String username, String password) {
//        Function<String, String> passwordEncoder
//                = input -> passwordEncoder().encode(input);
//
//        UserDetails userDetails = User.builder()
//                .passwordEncoder(passwordEncoder)
//                .username(username)
//                .password(password)
//                .roles("USER", "ADMIN")
//                .build();
//        return userDetails;
//    }
//
////    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
////    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(
//                        (requests) -> requests
//                                .requestMatchers("/user/login", "/user/logout", "/store/order", "/store/order/**").permitAll()
//                                .anyRequest()
//                                .authenticated())
//                .httpBasic(withDefaults());
//        http.csrf().disable();
//        return http.build();
//    }
//}