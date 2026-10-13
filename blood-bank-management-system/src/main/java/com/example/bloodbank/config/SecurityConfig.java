package com.example.bloodbank.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    UserDetailsService userDetailsService(
            @Value("${APP_ADMIN_USER:admin}") String adminUser,
            @Value("${APP_ADMIN_PASSWORD:ChangeAdminPassword!}") String adminPassword,
            @Value("${APP_STAFF_USER:staff}") String staffUser,
            @Value("${APP_STAFF_PASSWORD:ChangeStaffPassword!}") String staffPassword,
            PasswordEncoder encoder) {
        return new InMemoryUserDetailsManager(
            User.withUsername(adminUser).password(encoder.encode(adminPassword)).roles("ADMIN", "STAFF").build(),
            User.withUsername(staffUser).password(encoder.encode(staffPassword)).roles("STAFF").build()
        );
    }

    @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index.html", "/styles.css", "/app.js", "/favicon.ico").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll())
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
