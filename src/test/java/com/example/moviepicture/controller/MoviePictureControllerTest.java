package com.example.moviepicture.controller;

import com.example.moviepicture.models.entity.MoviePicture;
import com.example.moviepicture.service.MoviePictureService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(MoviePictureController.class)
@ExtendWith(MockitoExtension.class)
class MoviePictureControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MoviePictureService moviePictureService;


    @Test
    void getAllMoviePictures() throws Exception {
        List<MoviePicture> moviePictures = Arrays.asList(
                new MoviePicture(1L, "https://example.com/image1.jpg", 10, 2020),
                new MoviePicture(2L, "https://example.com/image2.jpg", 5, 2021)
        );

        when(moviePictureService.findAll()).thenReturn(moviePictures);

        mockMvc.perform(get("/movie-pictures"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].imageURL").value("https://example.com/image1.jpg"))
                .andExpect(jsonPath("$[0].favoritesCounter").value(10))
                .andExpect(jsonPath("$[0].releasedYear").value(2020))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].imageURL").value("https://example.com/image2.jpg"))
                .andExpect(jsonPath("$[1].favoritesCounter").value(5))
                .andExpect(jsonPath("$[1].releasedYear").value(2021));

        verify(moviePictureService, times(1)).findAll();
        verifyNoMoreInteractions(moviePictureService);
    }

    @Test
    void getMoviePicturesByReleasedYear() throws Exception {
        List<MoviePicture> moviePictures = Arrays.asList(
                new MoviePicture(1L, "https://example.com/image1.jpg", 10, 2020),
                new MoviePicture(2L, "https://example.com/image2.jpg", 5, 2020)
        );
        int releaseYear = 2020;

        when(moviePictureService.findByReleasedYear(anyInt())).thenReturn(moviePictures);

        mockMvc.perform(get("/movie-pictures/released-year/{releaseYear}",releaseYear))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].imageURL").value("https://example.com/image1.jpg"))
                .andExpect(jsonPath("$[0].favoritesCounter").value(10))
                .andExpect(jsonPath("$[0].releasedYear").value(releaseYear))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].imageURL").value("https://example.com/image2.jpg"))
                .andExpect(jsonPath("$[1].favoritesCounter").value(5))
                .andExpect(jsonPath("$[1].releasedYear").value(releaseYear));

        verify(moviePictureService, times(1)).findByReleasedYear(releaseYear);
        verifyNoMoreInteractions(moviePictureService);
    }

    @Test
    void votedMoviePictureUp() throws Exception {
        Optional<MoviePicture>  moviePicture = Optional.of(
                new MoviePicture(1L, "https://example.com/image1.jpg", 10, 2020)
        );
        Long id = 1L;
        when(moviePictureService.votedMoviePictureUp(anyLong())).thenReturn(moviePicture);

        mockMvc.perform(post("/movie-pictures/{id}/upvote",id))
                .andExpect(status().isOk());
    }
    @Test
    void votedMoviePictureUp_400() throws Exception {
        Optional<MoviePicture>  moviePicture = Optional.of(
                new MoviePicture(1L, "https://example.com/image1.jpg", 10, 2020)
        );
        Long id = 1L;
        when(moviePictureService.votedMoviePictureUp(anyLong())).thenReturn(moviePicture);

        mockMvc.perform(post("/movie-pictures/{id}/upvote",id))
                .andExpect(status().isBadRequest());
    }
    @Test
    void votedMoviePictureUp_null() throws Exception {
        Long id = 1L;
        when(moviePictureService.votedMoviePictureUp(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(post("/movie-pictures/{id}/upvote",id))
                .andExpect(status().isNotFound());
    }

    @Test
    void votedMoviePictureDown() throws Exception {
        Optional<MoviePicture>  moviePicture = Optional.of(
                new MoviePicture(2L, "https://example.com/image1.jpg", 10, 2020)
        );
        Long id = 2L;
        when(moviePictureService.votedMoviePictureDown(anyLong())).thenReturn(moviePicture);

        mockMvc.perform(post("/movie-pictures/{id}/downvote",id))
                .andExpect(status().isOk());
    }

    @Test
    void votedMoviePictureDown_400() throws Exception {
        Optional<MoviePicture>  moviePicture = Optional.of(
                new MoviePicture(1L, "https://example.com/image1.jpg", 10, 2020)
        );
        Long id = 1L;
        when(moviePictureService.votedMoviePictureDown(anyLong())).thenReturn(moviePicture);

        mockMvc.perform(post("/movie-pictures/{id}/downvote",id))
                .andExpect(status().isBadRequest());
    }

    @Test
    void votedMoviePictureDown_null() throws Exception {
        Long id = 3L;
        when(moviePictureService.votedMoviePictureDown(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(post("/movie-pictures/{id}/downvote",id))
                .andExpect(status().isNotFound());
    }

    @Test
    void getMoviePictureDetails() throws Exception {
        Optional<MoviePicture>  moviePicture = Optional.of(
                new MoviePicture(1L, "https://example.com/image1.jpg", 10, 2020)
        );
        Long id = 1L;
        when(moviePictureService.getMoviePictureDetails(anyLong())).thenReturn(moviePicture);

        mockMvc.perform(get("/movie-pictures/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.imageURL").value("https://example.com/image1.jpg"))
                .andExpect(jsonPath("$.favoritesCounter").value(10))
                .andExpect(jsonPath("$.releasedYear").value(2020));
    }
}