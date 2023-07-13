package com.agroniks.marketplace.user;

import com.agroniks.marketplace.user.jpa.UserEntity;
import com.agroniks.marketplace.user.jpa.UserEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserAuthService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userByName = userEntityRepository.findUserByUsername(username);

        if (userByName == null)
            throw new UsernameNotFoundException("User " + username + " not found");
        else
            log.info("DEV LOG: " + username + " found.");


        List<String> roles1 = userByName.getRoles();

        return User.builder().username(userByName.getUsername())
                .password(passwordEncoder.encode(userByName.getPassword()))
                .roles(roles1.toArray(new String[0])).build();


    }
}
