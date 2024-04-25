package com.github.onlinemovieservice.dto.movie;

import com.github.onlinemovieservice.model.Director;
import lombok.Data;

import java.time.Year;

@Data
public class MovieWithoutGenreDto {
    private String title;
    private Year year;
    private Director director;
}
