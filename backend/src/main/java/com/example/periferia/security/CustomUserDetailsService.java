package com.example.periferia.security;

import com.example.periferia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        System.out.println("load user");
        System.out.println(username);
        Optional<com.example.periferia.model.User> usuario = userRepository.findByEmail(username);
        if (usuario.isPresent()) {
            com.example.periferia.model.User user = usuario.get();
            return User.withUsername(user.getEmail())
                    .password(user.getPassword())  // password: "password" encriptado con BCrypt
                    .roles()
                    .build();
        }

        return null;
    }
}

