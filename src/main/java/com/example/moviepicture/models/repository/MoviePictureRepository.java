package com.example.moviepicture.models.repository;

import com.example.moviepicture.models.entity.MoviePicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoviePictureRepository extends JpaRepository<MoviePicture, Long> {
    List<MoviePicture> findByReleasedYear(int releasedYear);
}
