package com.example.periferia.service;

import com.example.periferia.model.Publication;
import com.example.periferia.model.User;
import com.example.periferia.repository.PublicationRepository;
import com.example.periferia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PublicationService {
    @Autowired
    private PublicationRepository publicacionRepository;

    @Autowired
    private UserRepository usuarioRepository;

    public Publication crearPublicacion(Long usuarioId, Publication publicacion) {
         publicacion.setUsuarioId(usuarioId);
        return publicacionRepository.save(publicacion);
    }

    public List<Publication> obtenerPublicacionesPorUsuario(Long usuarioId) {
        return publicacionRepository.findAll();
    }

}
