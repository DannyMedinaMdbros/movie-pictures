package com.example.moviepicture.controller;

import com.example.moviepicture.models.entity.MoviePicture;
import com.example.moviepicture.service.MoviePictureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/movie-pictures")
public class MoviePictureController {
    private MoviePictureService moviePictureService;
    private Set<Long> votedMoviePictureIds = new HashSet<>();
    public MoviePictureController(MoviePictureService moviePictureService) {
        this.moviePictureService = moviePictureService;
    }

    @GetMapping()
    public ResponseEntity<List<MoviePicture>> getAllMoviePictures() {
        return ResponseEntity.ok(moviePictureService.findAll());
    }

    @GetMapping("/released-year/{releasedYear}")
    public ResponseEntity<List<MoviePicture>> getMoviePicturesByReleasedYear(@PathVariable int releasedYear) {
        return ResponseEntity.ok(moviePictureService.findByReleasedYear(releasedYear));
    }

    @PostMapping("/{id}/upvote")
    public ResponseEntity<?> votedMoviePictureUp(@PathVariable Long id) {
        if (votedMoviePictureIds.contains(id)) {
            return ResponseEntity.badRequest().body("You have already voted for this picture.");
        }
        if(moviePictureService.votedMoviePictureUp(id).isPresent()){
            votedMoviePictureIds.add(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/downvote")
    public ResponseEntity<?> votedMoviePictureDown(@PathVariable Long id) {
        if (votedMoviePictureIds.contains(id)) {
            return ResponseEntity.badRequest().body("You have already voted for this picture.");
        }
        Optional<MoviePicture> optionalMoviePicture = moviePictureService.votedMoviePictureDown(id);
        if (optionalMoviePicture.isPresent()) {
            votedMoviePictureIds.add(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoviePicture> getMoviePictureDetails(@PathVariable Long id) {
        Optional<MoviePicture> optionalMoviePicture = moviePictureService.getMoviePictureDetails(id);
        return optionalMoviePicture.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
