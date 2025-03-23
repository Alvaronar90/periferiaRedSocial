package com.example.periferia.controller;

import com.example.periferia.dto.ResponseLoginDto;
import com.example.periferia.model.User;
import com.example.periferia.repository.UserRepository;
import com.example.periferia.security.JwtAuthFilter;
import com.example.periferia.service.UserService;
import com.example.periferia.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@Tag(name = "Auth Controller", description = "Controlador para la autenticación y registro de usuarios")
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
    @Operation(summary = "Registrar un nuevo usuario", description = "Permite registrar un nuevo usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado con éxito"),
            @ApiResponse(responseCode = "400", description = "Error al registrar el usuario", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user.getName(), user.getEmail(), user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al registrar el usuario: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Permite a un usuario autenticarse y obtener un token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso", content = @Content(schema = @Schema(implementation = ResponseLoginDto.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error al procesar la solicitud", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Object> login(@RequestBody User user) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

            if (userOptional.isPresent()) {
                User foundUser = userOptional.get();

                if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
                    String token = jwtAuthFilter.generateToken(foundUser.getEmail());
                    ResponseLoginDto dto = new ResponseLoginDto();
                    dto.setId(foundUser.getId());
                    dto.setToken(token);
                    dto.setName(foundUser.getName());
                    dto.setEmail(foundUser.getEmail());
                    return ResponseEntity.ok(dto);
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
