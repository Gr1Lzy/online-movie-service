package com.github.onlinemovieservice.dto.movie;

import com.github.onlinemovieservice.model.Director;
import com.github.onlinemovieservice.model.Genre;
import lombok.Data;

import java.time.Year;
import java.util.Set;

@Data
public class MovieDto {
    private Long id;
    private String title;
    private Year year;
    private Set<Genre> genres;
    private Director director;
}
