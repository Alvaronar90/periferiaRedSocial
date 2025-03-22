package com.example.periferia.controller;

import com.example.periferia.model.User;
import com.example.periferia.repository.UserRepository;
import com.example.periferia.security.JwtAuthFilter;
import com.example.periferia.service.UserService;
import com.example.periferia.exception.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // Endpoint para registrar un usuario
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            // Registrar al usuario
            userService.registerUser(user.getName(), user.getEmail(), user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al registrar el usuario: " + e.getMessage());
        }
    }

    // Endpoint para iniciar sesión (login)
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        try {
            // Buscar el usuario por email
            Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

            if (userOptional.isPresent()) {
                User foundUser = userOptional.get();

                // Verificar la contraseña
                if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
                    // Generar el token JWT
                    String token = jwtAuthFilter.generateToken(foundUser.getEmail());
                    return ResponseEntity.ok(token);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(new ApiError(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiError(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar la solicitud"));
        }
    }
}
