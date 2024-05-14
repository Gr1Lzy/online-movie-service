package com.github.onlinemovieservice.dto.movie;

import com.github.onlinemovieservice.model.Director;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieWithoutGenreDto {
    private String title;
    private LocalDate releaseDate;
    private Director director;
}
