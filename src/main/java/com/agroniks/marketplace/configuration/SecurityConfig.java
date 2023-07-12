package com.agroniks.marketplace.configuration;


import com.agroniks.marketplace.user.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    UserEntityService userEntityService;

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


//    @Bean
//    public UserDetailsService myUsersService(PasswordEncoder passwordEncoder) {
//        User.UserBuilder users = User.builder();
//        UserDetails commonUser = users.username("Andrew")
//                .password(passwordEncoder.encode("123"))
//                .roles("NORMAL")
//                .build();
//
//        UserDetails admin = users.username("Admin")
//                .password(passwordEncoder.encode("Zaq12wsx"))
//                .roles("ADMIN", "NORMAL")
//                .build();
//
//        UserDetails userWithNoRoles = users
//                .username("NoRoles")
//                .password(passwordEncoder.encode("pass"))
//                .roles()
//                .build();
//
//        List<UserDetails> userDetails = new ArrayList<>();
//        userDetails.add(commonUser);
//        userDetails.add(admin);
//        userDetails.add(userWithNoRoles);
//        System.out.println(userDetails);
//        return new InMemoryUserDetailsManager(userDetails);
//    }

}
