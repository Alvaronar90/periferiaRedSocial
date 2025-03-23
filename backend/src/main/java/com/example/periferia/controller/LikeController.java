package com.example.periferia.controller;

import com.example.periferia.model.Like;
import com.example.periferia.service.LikeService;
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
@RequestMapping("/likes")
@Tag(name = "Like Controller", description = "Endpoints para gestionar los likes de publicaciones")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/savelike")
    @Operation(summary = "Registrar un Like", description = "Guarda un like para una publicación y devuelve la lista actualizada de likes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Like registrado exitosamente",
                    content = @Content(schema = @Schema(implementation = Like.class))),
            @ApiResponse(responseCode = "500", description = "Error al guardar el like",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Object> registerLike(@RequestBody Like like) {
        try {
            // Llamar al servicio para guardar el "like"
            List<Like> likesGuardados = likeService.saveLike(like);
            return new ResponseEntity<>(likesGuardados, HttpStatus.CREATED);
        } catch (Exception e) {
            // Si ocurre un error, se captura y se envía la respuesta con error
            ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el like: " + e.getMessage());
            return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
