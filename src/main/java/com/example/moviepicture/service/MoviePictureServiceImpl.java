package com.example.moviepicture.service;

import com.example.moviepicture.models.entity.MoviePicture;
import com.example.moviepicture.models.repository.MoviePictureRepository;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class MoviePictureServiceImpl implements MoviePictureService{
    private MoviePictureRepository  moviePictureRepository;
    public MoviePictureServiceImpl(MoviePictureRepository moviePictureRepository) {
        this.moviePictureRepository = moviePictureRepository;
    }

    @Override
    public List<MoviePicture> findAll() {
        List<MoviePicture> moviePictures = moviePictureRepository.findAll();
        moviePictures.sort(Comparator.comparingInt(MoviePicture::getFavoritesCounter).reversed());
        return moviePictures;
    }

    @Override
    public List<MoviePicture> findByReleasedYear(int releasedYear) {
        List<MoviePicture> moviePictures = moviePictureRepository.findByReleasedYear(releasedYear);
        moviePictures.sort(Comparator.comparingInt(MoviePicture::getFavoritesCounter).reversed());
        return moviePictures;
    }

    @Override
    public Optional<MoviePicture> votedMoviePictureUp(Long id) {
        Optional<MoviePicture> optionalMoviePicture = moviePictureRepository.findById(id);
        if (optionalMoviePicture.isPresent()) {
            MoviePicture moviePicture = optionalMoviePicture.get();
            moviePicture.setFavoritesCounter(moviePicture.getFavoritesCounter() + 1);
            moviePictureRepository.save(moviePicture);
        }
        return optionalMoviePicture;
    }

    @Override
    public Optional<MoviePicture> votedMoviePictureDown(Long id) {
        Optional<MoviePicture> optionalMoviePicture = moviePictureRepository.findById(id);
        if (optionalMoviePicture.isPresent()) {
            MoviePicture moviePicture = optionalMoviePicture.get();
            moviePicture.setFavoritesCounter(moviePicture.getFavoritesCounter() - 1);
            moviePictureRepository.save(moviePicture);
        }
        return optionalMoviePicture;
    }

    @Override
    public Optional<MoviePicture> getMoviePictureDetails(Long id) {
        return moviePictureRepository.findById(id);
    }
}
