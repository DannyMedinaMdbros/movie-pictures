package com.example.moviepicture.service;

import com.example.moviepicture.models.entity.MoviePicture;
import com.example.moviepicture.models.repository.MoviePictureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MoviePictureServiceImplTest {
    @Mock
    private MoviePictureRepository moviePictureRepository;
    @InjectMocks
    private MoviePictureServiceImpl moviePictureService;
    @BeforeEach
    public void setup(){

    }
    @Test
    void findAll() {
        List<MoviePicture> moviePictures = Arrays.asList(
                new MoviePicture(1L,"path/movie-picture.png",12, 2019),
                new MoviePicture(2L,"path/movie-picture1.png",21, 1983),
                new MoviePicture(3L,"path/movie-picture2.png",31, 2004),
                new MoviePicture(4L,"path/movie-picture3.png",70, 2019)
        );
        given(moviePictureRepository.findAll()).willReturn(moviePictures);
        List<MoviePicture> moviePicturesReceived = moviePictureService.findAll();
        assertEquals(4, moviePicturesReceived.size());
    }

    @Test
    void findByReleasedYear() {
        List<MoviePicture> moviePictures = Arrays.asList(
                new MoviePicture(1L,"path/movie-picture.png",12, 2019),
                new MoviePicture(4L,"path/movie-picture3.png",70, 2019)
        );
        int releasedYear = 2019;
        given(moviePictureRepository.findByReleasedYear(anyInt())).willReturn(moviePictures);
        List<MoviePicture> moviePicturesReceived = moviePictureService.findByReleasedYear(releasedYear);
        assertEquals(2, moviePicturesReceived.size());
        Optional<MoviePicture> moviePicture = moviePicturesReceived.stream().findAny();
        moviePicture.ifPresent(picture -> assertEquals(releasedYear, picture.getReleasedYear()));
    }

    @Test
    void votedMoviePictureUp() {
        Optional<MoviePicture> optionalMoviePicture =
                Optional.of(
                        new MoviePicture(1L,"path/movie-picture.png",12, 2019)
                );
        Long id = 1L;
        given(moviePictureRepository.findById(anyLong())).willReturn(optionalMoviePicture);
        Optional<MoviePicture> optionalMoviePictureReceived = moviePictureService.votedMoviePictureUp(id);
        assertTrue(optionalMoviePictureReceived.isPresent());
    }

    @Test
    void votedMoviePictureDown() {
        Optional<MoviePicture> optionalMoviePicture =
                Optional.of(
                        new MoviePicture(1L,"path/movie-picture.png",12, 2019)
                );
        Long id = 1L;
        given(moviePictureRepository.findById(anyLong())).willReturn(optionalMoviePicture);
        Optional<MoviePicture> optionalMoviePictureReceived = moviePictureService.votedMoviePictureDown(id);
        assertTrue(optionalMoviePictureReceived.isPresent());
    }

    @Test
    void getMoviePictureDetails() {
        Optional<MoviePicture> optionalMoviePicture =
                Optional.of(
                        new MoviePicture(1L,"path/movie-picture.png",12, 2019)
                );
        Long id = 1L;
        given(moviePictureRepository.findById(anyLong())).willReturn(optionalMoviePicture);
        Optional<MoviePicture> optionalMoviePictureReceived = moviePictureService.getMoviePictureDetails(id);
        assertTrue(optionalMoviePictureReceived.isPresent());
    }
}