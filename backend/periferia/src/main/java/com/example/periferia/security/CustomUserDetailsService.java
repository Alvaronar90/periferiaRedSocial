package com.example.periferia.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Esto es solo un ejemplo en memoria. Conecta con tu base de datos si es necesario.
        return User.withUsername("user")
                .password("$2a$10$5vBsjN6/Pdd8v06I5.LS.eLpyr.HKTklyPS3W5NskZk9luuTP6A3W")  // password: "password" encriptado con BCrypt
                .roles("USER")
                .build();
    }
}
