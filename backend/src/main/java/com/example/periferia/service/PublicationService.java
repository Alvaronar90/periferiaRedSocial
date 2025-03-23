package com.example.periferia.service;

import com.example.periferia.model.Publication;
import com.example.periferia.model.User;
import com.example.periferia.repository.LikeRepository;
import com.example.periferia.repository.PublicationRepository;
import com.example.periferia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PublicationService {
    @Autowired
    private PublicationRepository publicacionRepository;

    @Autowired
    private UserRepository usuarioRepository;
    @Autowired
    private LikeRepository likeRepository;
    public Publication crearPublicacion(Long usuarioId, Publication publicacion) {
         publicacion.setUsuarioId(usuarioId);
        return publicacionRepository.save(publicacion);
    }

    public List<Publication> obtenerPublicacionesPorUsuario() {
        List<Publication> publicaciones = publicacionRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        for (Publication publication : publicaciones) {
            int likesCount = likeRepository.findByIdPublication(publication.getId()).size();
            publication.setLikes(likesCount);
        }

        return publicaciones;
    }


}
