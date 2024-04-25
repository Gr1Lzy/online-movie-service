package com.github.onlinemovieservice.dto.movie;

import com.github.onlinemovieservice.model.Director;
import com.github.onlinemovieservice.model.Genre;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@Data
public class MovieSaveDto {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Year is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;

    @NotBlank(message = "Genres are required")
    private Set<Genre> genres;

    @NotBlank(message = "Director is required")
    private Director director;
}
