package com.github.onlinemovieservice.dto.movie;

import com.github.onlinemovieservice.model.Director;
import com.github.onlinemovieservice.model.Genre;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class MovieDto {
    private Long id;
    private String title;
    private LocalDate releaseDate;
    private Set<Genre> genres;
    private Director director;
}
