package com.example.periferia.repository;

import com.example.periferia.model.Like;
import com.example.periferia.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    // Buscar publicaciones por usuario
    List<Like> findByIdPublication (Long publicationId);
}

