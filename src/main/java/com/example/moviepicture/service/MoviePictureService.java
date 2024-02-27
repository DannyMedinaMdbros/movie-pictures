package com.example.moviepicture.service;

import com.example.moviepicture.models.entity.MoviePicture;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface MoviePictureService {
    List<MoviePicture> findAll();
    List<MoviePicture> findByReleasedYear(int releasedYear);
    Optional<MoviePicture> votedMoviePictureUp(Long id);
    Optional<MoviePicture> votedMoviePictureDown(Long id);
    Optional<MoviePicture> getMoviePictureDetails(@PathVariable Long id);
}
