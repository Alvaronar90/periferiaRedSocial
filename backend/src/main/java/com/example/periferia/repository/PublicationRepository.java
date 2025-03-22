package com.example.periferia.repository;


import com.example.periferia.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
    // Buscar publicaciones por usuario
    List<Publication> findByUsuarioId (Long userId);
}

