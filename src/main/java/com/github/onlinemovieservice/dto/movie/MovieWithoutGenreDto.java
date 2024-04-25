package com.github.onlinemovieservice.dto.movie;

import com.github.onlinemovieservice.model.Director;
import lombok.Data;

import java.util.Date;

@Data
public class MovieWithoutGenreDto {
    private String title;
    private Date releaseDate;
    private Director director;
}
