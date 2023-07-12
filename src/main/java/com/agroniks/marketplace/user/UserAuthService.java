package com.agroniks.marketplace.user;

import com.agroniks.marketplace.user.jpa.UserEntity;
import com.agroniks.marketplace.user.jpa.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userByName = userEntityRepository.findUserByUsername(username);
        return User.builder().username(userByName.getUsername())
                .password(passwordEncoder.encode(userByName.getPassword()))
                .roles("ADMIN", "NORMAL")
                .build();
    }
}
