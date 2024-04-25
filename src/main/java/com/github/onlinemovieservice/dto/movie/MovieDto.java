package com.github.onlinemovieservice.dto.movie;

import com.github.onlinemovieservice.model.Director;
import com.github.onlinemovieservice.model.Genre;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class MovieDto {
    private Long id;
    private String title;
    private Date releaseDate;
    private Set<Genre> genres;
    private Director director;
}
