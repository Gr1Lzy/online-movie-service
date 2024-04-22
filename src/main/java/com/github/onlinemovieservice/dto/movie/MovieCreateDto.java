package com.github.onlinemovieservice.dto.movie;

import com.github.onlinemovieservice.model.Director;
import com.github.onlinemovieservice.model.Genre;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Year;
import java.util.Set;

@Data
public class MovieCreateDto {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Year is required")
    private Year year;

    @NotBlank(message = "Genres are required")
    private Set<Genre> genres;

    @NotBlank(message = "Director is required")
    private Director director;
}
