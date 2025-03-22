package com.example.periferia.controller;

import com.example.periferia.model.User;
import com.example.periferia.repository.UserRepository;
import com.example.periferia.security.JwtAuthFilter;
import com.example.periferia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.registerUser(user.getName(), user.getEmail(), user.getPassword());
        return ResponseEntity.ok("Usuario registrado con éxito");
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        // Buscar el usuario por email
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            User foundUser = userOptional.get();

            // Verificar la contraseña
            if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
                return jwtAuthFilter.generateToken(foundUser.getEmail());
            }
        }

        return "Credenciales inválidas";
    }

}
