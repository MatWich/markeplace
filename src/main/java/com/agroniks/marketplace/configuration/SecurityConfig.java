package com.agroniks.marketplace.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/v1/item/**").hasRole("NORMAL")
                        .requestMatchers("/api/v1/users/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                        ).csrf().disable()
                .httpBasic(Customizer.withDefaults());

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    @Bean
    public UserDetailsService myUsersService(PasswordEncoder passwordEncoder) {
        User.UserBuilder users = User.builder();
        UserDetails commonUser = users.username("Andrew")
                .password(passwordEncoder.encode("123"))
                .roles("NORMAL")
                .build();

        UserDetails admin = users.username("Admin")
                .password(passwordEncoder.encode("Zaq12wsx"))
                .roles("ADMIN", "NORMAL")
                .build();

        UserDetails userWithNoRoles = users.username("NoRoles")
                .password(passwordEncoder.encode("pass"))
                .roles()
                .build();


        return new InMemoryUserDetailsManager(commonUser, admin, userWithNoRoles);
    }

}
