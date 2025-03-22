package com.example.periferia.controller;

import com.example.periferia.model.Publication;
import com.example.periferia.service.PublicationService;
import com.example.periferia.exception.ApiError; // Asegúrate de importar ApiError
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publications")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class PublicationController {

    @Autowired
    private PublicationService publicacionService;

    // Método POST para crear una publicación
    @PostMapping("/savePublication/{userId}")
    public ResponseEntity<Object> crearPublicacion(@PathVariable Long userId, @RequestBody Publication publication) {
        try {
            Publication publicacionCreada = publicacionService.crearPublicacion(userId, publication);
            return new ResponseEntity<>(publicacionCreada, HttpStatus.CREATED);
        } catch (Exception e) {
            // Si ocurre un error en el servicio, será capturado aquí y enviado al GlobalExceptionHandler
            ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear la publicación: " + e.getMessage());
            return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método GET para obtener las publicaciones de un usuario
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
    @GetMapping("/getPublications")
    public ResponseEntity<Object> obtenerPublicaciones() {
        try {
            List<Publication> publicaciones = publicacionService.obtenerPublicacionesPorUsuario();
            return new ResponseEntity<>(publicaciones, HttpStatus.OK);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener publicaciones: " + e.getMessage());
            return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
