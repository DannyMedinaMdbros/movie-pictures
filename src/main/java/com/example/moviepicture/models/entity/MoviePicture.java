package com.example.moviepicture.models.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoviePicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "image_url")
    private String imageURL;
    @Column(name = "favorites_counter")
    private int favoritesCounter;
    @Column(name = "released_year")
    private int releasedYear;
}
