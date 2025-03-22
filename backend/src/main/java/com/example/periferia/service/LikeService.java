package com.example.periferia.service;

import com.example.periferia.model.Like;
import com.example.periferia.repository.LikeRepository;
import com.example.periferia.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    @Autowired
    LikeRepository likeRepository;

    public List<Like> saveLike(Like like){
        Like like1 = likeRepository.save(like);
        return likeRepository.findByIdPublication((long)like1.getIdPublication());
    }

}
