package com.example.periferia.controller;

import com.example.periferia.model.Like;
import com.example.periferia.model.Publication;
import com.example.periferia.service.LikeService;
import com.example.periferia.exception.ApiError; // Asegúrate de importar ApiError
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    LikeService likeService;

    @PostMapping("/savelike")
    public ResponseEntity<Object> registerLike(@RequestBody Like like) {
        try {
            // Llamar al servicio para guardar el "like"
            List<Like> likesGuardados = likeService.saveLike(like);
            return new ResponseEntity<>(likesGuardados, HttpStatus.CREATED);
        } catch (Exception e) {
            // Si ocurre un error en el servicio, lo capturamos y enviamos al cliente
            ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el like: " + e.getMessage());
            return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
