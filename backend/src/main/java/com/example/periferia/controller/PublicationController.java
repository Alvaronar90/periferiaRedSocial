package com.example.periferia.controller;

import com.example.periferia.model.Publication;
import com.example.periferia.service.PublicationService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publications")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@Tag(name = "Publication Controller", description = "Endpoints para gestionar publicaciones")
public class PublicationController {

    @Autowired
    private PublicationService publicacionService;

    @PostMapping("/savePublication/{userId}")
    @Operation(summary = "Crear publicación", description = "Crea una publicación para un usuario específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Publicación creada exitosamente",
                    content = @Content(schema = @Schema(implementation = Publication.class))),
            @ApiResponse(responseCode = "500", description = "Error al crear la publicación",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Object> crearPublicacion(@PathVariable Long userId, @RequestBody Publication publication) {
        try {
            Publication publicacionCreada = publicacionService.crearPublicacion(userId, publication);
            return new ResponseEntity<>(publicacionCreada, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear la publicación: " + e.getMessage());
            return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
    @GetMapping("/getPublications")
    @Operation(summary = "Obtener publicaciones", description = "Recupera la lista de publicaciones de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de publicaciones obtenido exitosamente",
                    content = @Content(schema = @Schema(implementation = Publication.class))),
            @ApiResponse(responseCode = "500", description = "Error al obtener publicaciones",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
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
